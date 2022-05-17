package com.savvycom.ui.posts

import androidx.lifecycle.lifecycleScope
import com.savvycom.R
import com.savvycom.base.BaseFragment
import com.savvycom.core.extensions.hideKeyboard
import com.savvycom.core.extensions.textChanges
import com.savvycom.core.utils.KeyboardUtils
import com.savvycom.data.response.PostModel
import com.savvycom.databinding.FragmentPostBinding
import com.savvycom.ui.comments.CommentFragment
import com.savvycom.ui.posts.adapter.PostAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import org.koin.androidx.viewmodel.ext.android.viewModel

@FlowPreview
@ExperimentalCoroutinesApi
class PostFragment : BaseFragment<FragmentPostBinding, PostViewModel>() {

    companion object {
        fun newInstance() = PostFragment()
    }

    override val layout = R.layout.fragment_post

    override val viewModel: PostViewModel by viewModel()

    private lateinit var mAdapter: PostAdapter

    override fun init() {
        mAdapter = PostAdapter(this::onItemClick)
        binding.rvPosts.adapter = mAdapter

        initListeners()
        viewModel.getListPost()
    }

    override fun initObservers() {
        viewModel.listPost.observe(viewLifecycleOwner) {
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
            viewModel.getListPost(true)
        }
        viewModel.initSearch(binding.edtSearch) {
            mAdapter.submitList(it)
        }

    }

    private fun onItemClick(data: PostModel, position: Int) {
        binding.root.hideKeyboard()
        getNavigationController()?.pushScreen(CommentFragment.newInstance(data), false)
    }
}