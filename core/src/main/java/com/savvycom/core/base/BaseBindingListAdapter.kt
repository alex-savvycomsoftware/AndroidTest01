package com.savvycom.core.base

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class BaseBindingListAdapter<T, B : ViewDataBinding>(
    diff: DiffUtil.ItemCallback<T>,
    @LayoutRes private val layout: Int,
    private val onItemClick: ((T, Int) -> Unit)? = null
) : ListAdapter<T, BaseBindingHolder<B>>(diff), Filterable {

    private var inflater: LayoutInflater? = null
    val originItems = mutableListOf<T>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseBindingHolder<B> {
        return BaseBindingHolder(
            DataBindingUtil.inflate(getInflaterInstance(parent.context), layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: BaseBindingHolder<B>, position: Int) {
        getItem(position)?.let { item ->
            holder.itemView.setOnClickListener {
                onItemClick?.invoke(item, holder.adapterPosition)
            }
            bindData(holder, item, position)
        }
    }


    private fun getInflaterInstance(context: Context): LayoutInflater {
        return if (inflater != null) {
            inflater!!
        } else {
            inflater = LayoutInflater.from(context)
            inflater!!
        }
    }

    open fun setOriginList(items: List<T>) {
        originItems.clear()
        originItems.addAll(items)
        submitList(items)
    }

    override fun getItemId(position: Int): Long = position.toLong()

    abstract fun bindData(holder: BaseBindingHolder<B>, data: T, position: Int)
    open fun filterCondition(item: T, textFilter: String): Boolean = false

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                if (constraint == null || constraint.isEmpty()) {
                    filterResults.values = originItems
                    return filterResults
                }
                val charSearch = constraint.toString().trim()

                val result = originItems.filter {
                    filterCondition(it, charSearch)
                }
                filterResults.values = result
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                val data = results?.values ?: return
                val result = ArrayList<T>(data as MutableList<T>)
                submitList(result)
            }
        }
    }
}


class BaseBindingHolder<B : ViewDataBinding>(val binding: B?) :
    RecyclerView.ViewHolder(binding!!.root)