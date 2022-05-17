package com.savvycom.ui.posts.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.savvycom.core.base.BaseHolder
import com.savvycom.core.base.BaseListAdapter
import com.savvycom.data.response.PostModel
import com.savvycom.databinding.ItemPostBinding

class PostAdapter(onItemClick: ((PostModel, Int) -> Unit)) : BaseListAdapter<PostModel, ItemPostBinding>(postDiffUtils, onItemClick) {
    override fun bindData(holder: BaseHolder<ItemPostBinding>, data: PostModel, position: Int) {
        holder.binding?.post = data
    }

    override fun createViewBinding(inflater: LayoutInflater, parent: ViewGroup): ItemPostBinding {
        return ItemPostBinding.inflate(inflater, parent, false)
    }
}

val postDiffUtils = object: DiffUtil.ItemCallback<PostModel>(){
    override fun areItemsTheSame(oldItem: PostModel, newItem: PostModel): Boolean {
        return oldItem.id == newItem.id && oldItem.userId == newItem.userId
    }

    override fun areContentsTheSame(oldItem: PostModel, newItem: PostModel): Boolean {
        return (oldItem.title == newItem.title || oldItem.title?.equals(newItem.body, true) == true)
                && (oldItem.body == newItem.body || oldItem.body?.equals(newItem.body, true) == true)
    }

}