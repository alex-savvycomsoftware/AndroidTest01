package com.savvycom.core.utils

import android.view.View
import android.widget.TextView
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.savvycom.core.extensions.safeParseColor
import java.text.DecimalFormat

//import com.metaaivi.core.extensions.safeParseColor

@BindingAdapter("isVisible")
fun setIsVisible(view: View, isVisible: Boolean?) {
    view.isVisible = isVisible == true
}

@BindingAdapter("isTextVisible")
fun setTextVisible(view: View, isVisible: Boolean?) {
    view.isInvisible = isVisible != true
}

@BindingAdapter("errorMessage")
fun setErrorMessage(view: TextView, message: String?) {
    view.isVisible = !message.isNullOrBlank()
    view.text = message
}

@BindingAdapter("stringBackgroundColor")
fun setBackgroundColor(view: View, backgroundColor: String?) {
    view.setBackgroundColor(backgroundColor?.safeParseColor() ?: 0)
}

@BindingAdapter("price")
fun setPrice(view: TextView, price: Float){
    val format = DecimalFormat("#.#")
    view.text = "$${format.format(price)}"
}