package com.savvycom.core.base

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.manager.SupportRequestManagerFragment
import com.savvycom.core.navigation.NavigationController

open class BaseActivity : AppCompatActivity(), FragmentManager.OnBackStackChangedListener {

    protected var lastFragmentTag = ""
    protected lateinit var navController: NavigationController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navController = NavigationController(this)
    }
    override fun onResume() {
        super.onResume()
        supportFragmentManager.addOnBackStackChangedListener(this)
    }

    override fun onPause() {
        super.onPause()
        supportFragmentManager.removeOnBackStackChangedListener(this)
    }

    override fun onBackStackChanged() {
        val currentFragment = getCurrentFragment()
        if (currentFragment != null) {
            val newTag = currentFragment.javaClass.canonicalName?:""
            val oldFragment = supportFragmentManager.findFragmentByTag(lastFragmentTag)
            if (newTag != lastFragmentTag) {
                if (oldFragment is BaseBindingFragment<*>) {
                    oldFragment.onFragmentPaused()
                }
                if (currentFragment is BaseBindingFragment<*> && currentFragment.isFragmentPaused) {
                    currentFragment.onFragmentResumed()
                }
                lastFragmentTag = newTag
            }
        }
    }

    protected fun getCurrentFragment(): Fragment? {
        val fragments = supportFragmentManager.fragments
        if (fragments.isNotEmpty()) {
            var currentFragment = fragments.lastOrNull()
            if (currentFragment is SupportRequestManagerFragment) {
                currentFragment = if (fragments.size > 1) {
                    fragments[fragments.size - 2]
                } else {
                    null
                }
            }
            return currentFragment
        }
        return null

    }

    fun getNavigationController() = navController
}