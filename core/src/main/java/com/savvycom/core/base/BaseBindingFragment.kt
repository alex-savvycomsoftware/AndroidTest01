package com.savvycom.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.bumptech.glide.manager.SupportRequestManagerFragment
import com.savvycom.core.navigation.NavigationController


abstract class BaseBindingFragment<DB : ViewDataBinding> : Fragment() {
    open var hideStatusBar = false

    private var _binding: DB? = null
    val binding : DB by lazy {_binding!!}

    abstract val layout: Int

    abstract fun getNavigationController() : NavigationController?

    var isFragmentPaused = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(
            inflater,
            layout,
            container,
            false
        )
        binding.lifecycleOwner = this
        setupStatusBar()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.root.isClickable = true
        binding.root.setOnClickListener {

        }
        init()
        initObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    abstract fun init()

    protected open fun initObservers(){}

    private fun setupStatusBar() {
        if (hideStatusBar) {
            WindowCompat.setDecorFitsSystemWindows(requireActivity().window, false)
            WindowInsetsControllerCompat(requireActivity().window, binding.root).let { controller ->
                controller.hide(WindowInsetsCompat.Type.systemBars())
                controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
            requireActivity().actionBar?.hide()
        } else {
            WindowCompat.setDecorFitsSystemWindows(requireActivity().window, true)
            WindowInsetsControllerCompat(requireActivity().window, binding.root).show(WindowInsetsCompat.Type.systemBars())
            requireActivity().actionBar?.show()
        }
    }

    fun openScreen(fragment: Fragment) {
        getNavigationController()?.pushScreen(fragment, false)
    }

    fun popToScreen(fragment: Fragment) {
        getNavigationController()?.popToScreen(fragment)
    }

    fun openScreenWithOption(fragment: Fragment, popAllScreen: Boolean?) {
        if (popAllScreen != null && popAllScreen) {
            getNavigationController()?.popAllAndPushScreen(fragment)
        }
    }

    fun back() {
        getNavigationController()?.back()
    }

    fun isActive() : Boolean{
        val fragments = activity?.supportFragmentManager?.fragments?:ArrayList()
        if (fragments.isNotEmpty()){
            var lastFragment = fragments.lastOrNull()
            if (lastFragment is SupportRequestManagerFragment) {
                lastFragment = if (fragments.size > 1) {
                    fragments[fragments.size - 2]
                } else {
                    null
                }
            }
            return lastFragment == this
        }
        return false
    }

    open fun onFragmentPaused(){
        if (!isFragmentPaused) {
            isFragmentPaused = true
        }
        checkEnableViews(false)
    }

    open fun onFragmentResumed(){
        if (isFragmentPaused) {
            isFragmentPaused = true
        }
        checkEnableViews(true)
    }

    protected open fun checkEnableViews(isEnable: Boolean) {}
}