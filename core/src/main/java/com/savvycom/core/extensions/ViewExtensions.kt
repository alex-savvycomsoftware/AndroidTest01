/*
 * Copyright (C) 2017 - present Instructure, Inc.
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */
package com.savvycom.core.extensions

import android.content.Context
import android.view.View
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

fun EditText.content() = text?.toString()?.trim() ?: ""

fun EditText.clear() {
    setText("")
}

fun EditText.enableFocus(enable: Boolean) {
    isFocusable = enable
    isFocusableInTouchMode = enable
    isEnabled = enable
    if (!enable) {
        isSelected = false
    }
}

/**
 * Show the keyboard
 */
fun View.showKeyboard() {
    postOnAnimationDelayed({
        requestFocus()
        val imm = (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }, 300)
}

/**
 * Hide the keyboard
 */
fun View.hideKeyboard() {
    clearFocus()
    val inputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
}


inline fun View.afterMeasured(crossinline block: () -> Unit) {
    if (measuredWidth > 0 && measuredHeight > 0) {
        block()
    } else {
        viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                if (measuredWidth > 0 && measuredHeight > 0) {
                    viewTreeObserver.removeOnGlobalLayoutListener(this)
                    block()
                }
            }
        })
    }
}