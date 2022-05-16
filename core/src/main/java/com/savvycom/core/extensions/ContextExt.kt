package com.savvycom.core.extensions

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Point
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.savvycom.core.interfaces.OnClickAlertDialogListener
import java.io.File


fun Context.isPermissionGranted(permission: String): Boolean {
    return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
}

fun Context.isPermissionsGranted(vararg permissions: String): Boolean {
    return permissions.find {
        !this.isPermissionGranted(it)
    } == null
}

fun Fragment.isPermissionGranted(permission: String): Boolean {
    return requireContext().isPermissionGranted(permission)
}

fun Fragment.isPermissionsGranted(vararg permissions: String): Boolean {
    return requireContext().isPermissionsGranted(*permissions)
}

fun Context.getColorByRes(resourceId: Int): Int {
    return ContextCompat.getColor(this, resourceId)
}

fun Context.getDrawableByRes(resourceId: Int): Drawable? {
    return try {
        ResourcesCompat.getDrawable(resources, resourceId, null)
    } catch (ex: Resources.NotFoundException) {
        null
    }
}

fun Context.getFontByRes(resourceId: Int): Typeface? {
    return try {
        ResourcesCompat.getFont(this, resourceId)
    } catch (ex: Resources.NotFoundException) {
        null
    }
}

fun Context.showAlertDialog(
    message: String, negative: String? = null,
    positive: String?, isCancellable: Boolean = true, onClick: OnClickAlertDialogListener? = null
): AlertDialog {
    return AlertDialog.Builder(this)
        .setMessage(message)
        .setPositiveButton(positive) { dialog, _ ->
            onClick?.onClickPostive()
            dialog.dismiss()
        }.setNegativeButton(negative) { dialog, _ ->
            onClick?.onClickPostive()
            dialog.dismiss()
        }
        .setCancelable(isCancellable)
        .show()
}

fun Context.getResourceId(typeName: String, resourceName: String): Int {
    return resources.getIdentifier(resourceName, typeName, packageName)
}

fun Context.getStringResId(resourceName: String): Int {
    return getResourceId("string", resourceName)
}

fun Context.getDrawableResId(resourceName: String): Int {
    return getResourceId("drawable", resourceName)
}

fun Context.getRawResId(resourceName: String): Int {
    return getResourceId("raw", resourceName)
}

fun Context.getStringByName(resourceName: String): String {
    val resId = getResourceId("string", resourceName)
    return if (resId != 0) {
        resources.getString(resId)
    } else {
        ""
    }
}

fun Context.getDrawableByName(resourceName: String): Drawable? {
    val resId = getResourceId("drawable", resourceName)
    return if (resId != 0) {
        getDrawableCompat(resId)
    } else {
        null
    }
}

/**
 * Returns a non-null drawable using [ContextCompat]. Throws a [Resources.NotFoundException] if the specified
 * resource does not exist or there was another problem obtaining the drawable.
 */
fun Context.getDrawableCompat(@DrawableRes resId: Int): Drawable =
    ContextCompat.getDrawable(this, resId)
        ?: throw Resources.NotFoundException("Unable to obtain drawable from resource ID $resId")

fun Activity.setTransparentStatusBar() {
    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    window.statusBarColor = Color.TRANSPARENT
}

fun Context.dp2Px(dp: Int): Int {
    return dp2PxFloat(dp.toFloat())
}

fun Context.dp2PxFloat(dp: Float): Int {
    val displayMetrics = resources.displayMetrics
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics)
        .toInt()
}

fun Context.sp2Px(dp: Int): Int {
    val displayMetrics = resources.displayMetrics
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, dp.toFloat(), displayMetrics)
        .toInt()
}

fun Context.getStatusBarHeight(): Int {
    var statusBarHeight = 0
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        statusBarHeight = resources.getDimensionPixelSize(resourceId)
    }
    return statusBarHeight
}

fun Context.getDisplayDimension(fullScreen: Boolean = false): DisplayMetrics {
    val windowService = getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val displayMetrics = DisplayMetrics()
    if (fullScreen) {
        if (Build.VERSION.SDK_INT >= 30) {
            display?.getRealMetrics(displayMetrics)
        } else {
            windowService.defaultDisplay.getRealMetrics(displayMetrics)
        }
    } else {
        windowService.defaultDisplay.getMetrics(displayMetrics)
    }
    return displayMetrics
}

@RequiresApi(Build.VERSION_CODES.R)
fun Activity.getAppUsableSize(): Point {
    val point = Point()
    display?.getSize(point)
    return point
}

fun Context.getScreenWidth(): Int {
    val windowService = getSystemService(Context.WINDOW_SERVICE) as WindowManager
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val windowMetrics = windowService.currentWindowMetrics
        val insets = windowMetrics.windowInsets
            .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
        windowMetrics.bounds.width() - insets.left - insets.right
    } else {
        val displayMetrics = DisplayMetrics()
        windowService.defaultDisplay.getMetrics(displayMetrics)
        displayMetrics.widthPixels
    }
}

fun Context.getScreenDensity(): Float {
    val windowService = getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val displayMetrics = DisplayMetrics()
    windowService.defaultDisplay.getMetrics(displayMetrics)
    return displayMetrics.density
}

@SuppressLint("MissingPermission")
fun Context.isNetworkConnected(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val nw = connectivityManager.activeNetwork ?: return false
    val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
    return actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
            || actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
            || actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
            || actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH)
            || actNw.hasTransport(NetworkCapabilities.TRANSPORT_VPN)
}

fun Context.getFileUri(file: File): Uri? {
    return try {
        FileProvider.getUriForFile(this, "${packageName}.provider", file)
    } catch (exception: Exception) {
        Uri.fromFile(file)
    }
}

fun Context.toast(@StringRes resId: Int, toastLength: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(this, resId, toastLength).show()

fun Context.toast(text: String, toastLength: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(this, text, toastLength).show()

fun Context.isNightMode(): Boolean {
    return resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
}
