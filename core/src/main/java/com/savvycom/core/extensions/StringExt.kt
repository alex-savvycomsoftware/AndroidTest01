package com.savvycom.core.extensions

import android.graphics.Color

fun String.safeParseColor(): Int {
    return try {
        Color.parseColor(this)
    } catch (ex: Exception) {
        0
    }
}

fun String.safeToInt(fallback: Int = 0): Int{
    return try {
        toInt()
    } catch (ex: Exception) {
        0
    }
}

fun String.safeToFloat(fallback: Float = 0f): Float{
    return try {
        toFloatOrNull()?:0f
    } catch (ex: Exception) {
        0f
    }
}