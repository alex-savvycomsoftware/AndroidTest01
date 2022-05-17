package com.savvycom.ui.posts

import com.savvycom.R
import com.savvycom.base.BaseFragment
import com.savvycom.core.base.BaseBindingFragment
import com.savvycom.core.navigation.NavigationController
import com.savvycom.data.response.PostModel
import com.savvycom.databinding.FragmentPostBinding
import com.savvycom.ui.comments.CommentFragment
import com.savvycom.ui.posts.adapter.PostAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.dsl.viewModel
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
    }

    private fun onItemClick(data: PostModel, position: Int) {
        getNavigationController()?.pushScreen(CommentFragment.newInstance(data), false)
    }
}