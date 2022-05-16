package com.savvycom.base

import android.os.Bundle
import android.transition.TransitionInflater
import androidx.databinding.ViewDataBinding
import com.savvycom.MainActivity
import com.savvycom.R
import com.savvycom.core.base.BaseActivity
import com.savvycom.core.base.BaseBindingFragment
import com.savvycom.core.navigation.NavigationController
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
abstract class BaseFragment<DB : ViewDataBinding> : BaseBindingFragment<DB>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = TransitionInflater.from(requireContext())
        enterTransition = inflater.inflateTransition(R.transition.slide_left)
        exitTransition = inflater.inflateTransition(R.transition.slide_right)
    }

    override fun getNavigationController(): NavigationController? {
        return if (activity is BaseActivity){
            (activity as? BaseActivity)?.getNavigationController()
        }else{
            null
        }
    }
}