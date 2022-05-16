package com.savvycom.core.navigation

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.savvycom.core.R

open class NavigationController(activity: AppCompatActivity) {
    protected val _activity = activity

    fun pushScreen(fragment: Fragment, replace: Boolean) {
        try {
            val fragmentTransaction = if (replace) {
                _activity.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, fragment, fragment.javaClass.canonicalName)
            } else {
                _activity.supportFragmentManager
                    .beginTransaction()
                    .add(R.id.container, fragment, fragment.javaClass.canonicalName)
            }
            fragmentTransaction.addToBackStack(fragment.javaClass.canonicalName)
                .commitAllowingStateLoss()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }
    }

    fun popAllAndPushScreen(fragment: Fragment) {
        try {
            _activity.supportFragmentManager.popBackStackImmediate(
                null,
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
            pushScreen(fragment, false)
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }
    }

    fun popToScreen(fragment: Fragment) {
        try {
            _activity.supportFragmentManager.popBackStackImmediate(
                fragment.javaClass.canonicalName,
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
            pushScreen(fragment, true)
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }
    }

    fun back() {
        _activity.onBackPressed()
    }
}