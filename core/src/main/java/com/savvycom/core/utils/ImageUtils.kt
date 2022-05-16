package com.savvycom.core.utils

import android.content.Context
import android.graphics.*
import android.media.ExifInterface
import android.media.Image
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey
import java.io.File
import java.io.FileOutputStream


fun ImageView.displayRoundedImage(
    url: String?,
    radius: Int,
    skipCache: Boolean = false
) {
    val multipleTransform =
        MultiTransformation(
            CenterCrop(),
            RoundedCorners(radius)
        )
    val requestOptions =
        RequestOptions().format(DecodeFormat.PREFER_RGB_565).transform(multipleTransform)
//    val thumbnail = Glide.with(imageView.context)
//        .load(
//            if (isVertical) {
//                R.drawable.ic_story_cover_place_holder
//            } else {
//                R.drawable.ic_default_preview
//            }
//        )
//        .apply(requestOptions)
    var request = if (url?.startsWith("http") == true) {
        Glide.with(context)
            .load(url)
            .apply(requestOptions)
//            .thumbnail(thumbnail)
            .skipMemoryCache(skipCache)
    } else {
        Glide.with(context)
            .load(File(url ?: ""))
            .apply(requestOptions)
//            .thumbnail(thumbnail)
            .skipMemoryCache(skipCache)
    }
    if (skipCache) {
        request = request.signature(ObjectKey(System.currentTimeMillis()))
    }
    request.into(this)
}

fun ImageView.displayRoundedImage(
    uri: Uri?,
    radius: Int,
    skipCache: Boolean = false
) {
    val multipleTransform =
        MultiTransformation(
            CenterCrop(),
            RoundedCorners(radius)
        )
    val requestOptions =
        RequestOptions().format(DecodeFormat.PREFER_RGB_565).transform(multipleTransform)
//    val thumbnail = Glide.with(imageView.context)
//        .load(
//            if (isVertical) {
//                R.drawable.ic_story_cover_place_holder
//            } else {
//                R.drawable.ic_default_preview
//            }
//        )
//        .apply(requestOptions)
    var request = Glide.with(context)
        .load(uri)
        .apply(requestOptions)
//            .thumbnail(thumbnail)
        .skipMemoryCache(skipCache)
    if (skipCache) {
        request = request.signature(ObjectKey(System.currentTimeMillis()))
    }
    request.into(this)
}

fun ImageView.displayCircleImage(url: String?) {
    val requestOptions =
        RequestOptions().format(DecodeFormat.PREFER_RGB_565).transform(CircleCrop())
//    val thumbnail = Glide.with(imageView.context)
//        .load(R.drawable.ic_default_preview)
//        .apply(requestOptions)
    if (url?.startsWith("http") == true) {
        Glide.with(context)
            .load(url)
            .apply(requestOptions)
//            .thumbnail(thumbnail)
            .into(this)
    } else {
        Glide.with(context)
            .load(File(url ?: ""))
            .apply(requestOptions)
//            .thumbnail(thumbnail)
            .into(this)
    }
}

fun AppCompatImageView.displayCircleImage(resId: Int) {
    Glide.with(context)
        .load(resId)
        .apply(RequestOptions().transform(CircleCrop()))
        .into(this)
}

fun ImageView.displayCircleImage(uri: Uri?) {
    Glide.with(context)
        .load(uri)
        .apply(RequestOptions().transform(CircleCrop()))
        .into(this)
}


fun ImageView.displayImage(path: String?) {
    val requestOptions =
        RequestOptions().format(DecodeFormat.PREFER_RGB_565).centerCrop()
//    val thumbnail = Glide.with(imageView.context)
//        .load(R.drawable.ic_default_preview)
//        .apply(requestOptions)
    if (path?.startsWith("http") == true) {
        Glide.with(context)
            .load(path)
            .apply(requestOptions)
//            .thumbnail(thumbnail)
            .into(this)
    } else {
        Glide.with(context)
            .load(File(path ?: ""))
            .apply(requestOptions)
//            .thumbnail(thumbnail)
            .into(this)
    }
}

fun ImageView.displayImage(uri: Uri?) {
    val requestOptions =
        RequestOptions().format(DecodeFormat.PREFER_RGB_565).centerCrop()
//    val thumbnail = Glide.with(imageView.context)
//        .load(R.drawable.ic_default_preview)
//        .apply(requestOptions)
    Glide.with(context)
        .load(uri)
        .apply(requestOptions)
//            .thumbnail(thumbnail)
        .into(this)
}

fun rotateImage(source: Bitmap, angle: Float, isFlipHorizontal: Boolean = false): Bitmap {
    val matrix = Matrix()
    matrix.postRotate(angle)
    if (isFlipHorizontal) {
        matrix.postScale(-1f, 1f, source.width / 2f, source.height / 2f)
    }
    return Bitmap.createBitmap(
        source, 0, 0, source.width, source.height,
        matrix, true
    )
}

fun rotateImageCameraIfNeed(photoPath: String): Bitmap {
    val ei = ExifInterface(photoPath)
    val bitmap = BitmapFactory.decodeFile(photoPath)
    return when (ei.getAttributeInt(
        ExifInterface.TAG_ORIENTATION,
        ExifInterface.ORIENTATION_UNDEFINED
    )) {
        ExifInterface.ORIENTATION_ROTATE_90 ->
            rotateImage(bitmap, 90f)
        ExifInterface.ORIENTATION_ROTATE_180 ->
            rotateImage(bitmap, 180f)
        ExifInterface.ORIENTATION_ROTATE_270 ->
            rotateImage(bitmap, 270f)
        ExifInterface.ORIENTATION_TRANSVERSE ->
            rotateImage(bitmap, 270f, true)
        ExifInterface.ORIENTATION_NORMAL ->
            bitmap
        else -> {
            bitmap
        }
    }
}

fun rotateImageCameraIfNeed(path: String, inputBitmap: Bitmap): Bitmap {
    val ei = ExifInterface(path)
    return when (ei.getAttributeInt(
        ExifInterface.TAG_ORIENTATION,
        ExifInterface.ORIENTATION_UNDEFINED
    )) {
        ExifInterface.ORIENTATION_ROTATE_90 ->
            rotateImage(inputBitmap, 90f)
        ExifInterface.ORIENTATION_ROTATE_180 ->
            rotateImage(inputBitmap, 180f)
        ExifInterface.ORIENTATION_ROTATE_270 ->
            rotateImage(inputBitmap, 270f)
        ExifInterface.ORIENTATION_TRANSVERSE ->
            rotateImage(inputBitmap, 270f, true)
        ExifInterface.ORIENTATION_NORMAL ->
            inputBitmap
        else -> {
            inputBitmap
        }
    }
}

fun shouldRotateImage(path: String): Boolean {
    val ei = ExifInterface(path)
    val rotateAttribute = ei.getAttributeInt(
        ExifInterface.TAG_ORIENTATION,
        ExifInterface.ORIENTATION_UNDEFINED
    )
    return rotateAttribute == ExifInterface.ORIENTATION_ROTATE_90
            || rotateAttribute == ExifInterface.ORIENTATION_ROTATE_180
            || rotateAttribute == ExifInterface.ORIENTATION_ROTATE_270
            || rotateAttribute == ExifInterface.ORIENTATION_TRANSVERSE
}

fun compressBitmap(bitmap: Bitmap, fileOutputStream: FileOutputStream): Bitmap {
    val scale = when {
        bitmap.width >= 1500 -> 1 / 4f
        bitmap.width >= 1080 -> 1 / 2f
        bitmap.width > 500 -> 2 / 3f
        else -> 1f
    }
    val scaledBitmap = Bitmap.createScaledBitmap(
        bitmap,
        (bitmap.width * scale).toInt(),
        (bitmap.height * scale).toInt(),
        true
    )
    scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, fileOutputStream)
    return scaledBitmap
}

fun Image.toBitmap(context: Context): Bitmap? {
    try {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        YuvToRgbConverter(context).yuvToRgb(this, bitmap)
        return bitmap
    } catch (ex: Exception) {
        Log.e("TAG", "width: $width, height: $height")
        ex.printStackTrace()
        return null
    }
}