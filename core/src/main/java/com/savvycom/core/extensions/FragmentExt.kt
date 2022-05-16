package com.savvycom.core.extensions

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

fun Fragment.toast(@StringRes resId: Int, toastLength: Int = Toast.LENGTH_SHORT) =
    requireContext().toast(resId, toastLength)

fun Fragment.toast(text: String, toastLength: Int = Toast.LENGTH_SHORT) =
    requireContext().toast(text, toastLength)
