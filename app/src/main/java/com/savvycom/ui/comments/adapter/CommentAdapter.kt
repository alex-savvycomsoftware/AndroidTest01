package com.savvycom.ui.posts.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.savvycom.core.base.BaseHolder
import com.savvycom.core.base.BaseListAdapter
import com.savvycom.data.response.CommentModel
import com.savvycom.databinding.ItemCommentBinding

class CommentAdapter : BaseListAdapter<CommentModel, ItemCommentBinding>(commentDiffUtils) {

    override fun bindData(holder: BaseHolder<ItemCommentBinding>, data: CommentModel, position: Int) {
        holder.binding?.comment = data
    }

    override fun createViewBinding(inflater: LayoutInflater, parent: ViewGroup): ItemCommentBinding {
        return ItemCommentBinding.inflate(inflater, parent, false)
    }
}

val commentDiffUtils = object: DiffUtil.ItemCallback<CommentModel>() {
    override fun areItemsTheSame(oldItem: CommentModel, newItem: CommentModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CommentModel, newItem: CommentModel): Boolean {
        return (oldItem.body == newItem.body || oldItem.body?.equals(newItem.body, true) == true)
    }

}