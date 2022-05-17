package com.savvycom.ui.comments

import android.os.Bundle
import com.savvycom.R
import com.savvycom.base.BaseFragment
import com.savvycom.data.response.PostModel
import com.savvycom.databinding.FragmentCommentBinding
import com.savvycom.ui.posts.adapter.CommentAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.ext.android.viewModel

@FlowPreview
@ExperimentalCoroutinesApi
class CommentFragment : BaseFragment<FragmentCommentBinding, CommentViewModel>() {

    val mAdapter: CommentAdapter by lazy { CommentAdapter() }

    companion object {
        const val ARG_POST = "ARG_POST"

        fun newInstance(data: PostModel): CommentFragment {
            val args = Bundle()
            args.putParcelable(ARG_POST, data)
            return CommentFragment().apply {
                arguments = args
            }
        }
    }

    override val viewModel: CommentViewModel by viewModel()

    override val layout: Int = R.layout.fragment_comment

    override fun init() {
        binding.viewModel = viewModel
        viewModel.postData.value = arguments?.getParcelable(ARG_POST)
        binding.rvComments.adapter = mAdapter
        initListeners()
        viewModel.getListComment()
    }

    override fun initObservers() {

        viewModel.listComment.observe(viewLifecycleOwner) {
            mAdapter.submitList(it ?: ArrayList())
        }
        viewModel.errorCode.observe(viewLifecycleOwner) {

        }
        viewModel.isRefreshing.observe(viewLifecycleOwner) {
            binding.refreshLayout.isRefreshing = it ?: false
        }
    }

    private fun initListeners() {
        binding.refreshLayout.setOnRefreshListener {
            viewModel.getListComment(true)
        }
    }

}