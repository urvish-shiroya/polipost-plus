package com.polipost.core.extentions

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.util.Base64
import android.view.View
import com.polipost.core.R
import java.io.File
import java.io.FileOutputStream
import java.net.URL

fun Bitmap.flipHorizontally(): Bitmap {
    val matrix = Matrix().apply { postScale(-1f, 1f, width / 2f, height / 2f) }
    return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
}

fun View?.getOriginalBitmapFromView(): Bitmap? {
    return try {
        val view = this ?: return null

        if (view.measuredHeight > 0 && view.measuredWidth > 0) {
            val returnedBitmap =
                Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(returnedBitmap)
            val bgDrawable = view.background
            if (bgDrawable != null) {
                bgDrawable.draw(canvas)
            } else {
                canvas.drawColor(Color.TRANSPARENT)
            }
            view.draw(canvas)
            returnedBitmap
        } else {
            null
        }
    } catch (e: Exception) {
        println(e.message)
        null
    } catch (e: OutOfMemoryError) {
        println(e.message)
        null
    }
}

fun String?.getBitmapFromBase64(): Bitmap? {
    return try {
        if (checkStringValue(this)) {
            val encodedImage = this.toString()
            val pureBase64Encoded = encodedImage.substring((encodedImage.indexOf(",") + 1))
            val decodedString = Base64.decode(pureBase64Encoded, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        } else null
    } catch (_: Exception) {
        null
    }
}

fun Context?.downloadImageFromUrl(
    imageUrl: String?,
    outputDirectory: File?,
    fileName: String,
    compressFormat: Bitmap.CompressFormat = Bitmap.CompressFormat.PNG,
    callback: (Result<File>) -> Unit
) {
    val mContext = this ?: kotlin.run {
        ensureIsOnMainThread { callback(Result.failure(Throwable("Context is null"))) }
        return
    }
    val mOutputFile = outputDirectory ?: kotlin.run {
        ensureIsOnMainThread { callback(Result.failure(Throwable(mContext.getCompatString(R.string.const_output_file_null_error)))) }
        return
    }
    ensureIsOnBackgroundThread {
        if (checkStringValue(imageUrl)) {
            try {
                val mSampleBitmap: Bitmap? = if (imageUrl.toString().startsWith("data")) {
                    imageUrl.getBitmapFromBase64()
                } else {
                    URL(imageUrl.toString()).openStream().use {
                        BitmapFactory.decodeStream(it)
                    } ?: null
                }

                mOutputFile.createDirectory() // Required.
                val finalDestination = File(mOutputFile, fileName)
                if (mSampleBitmap.isNotNull()) {
                    val mOutputStream = FileOutputStream(finalDestination)
                    mSampleBitmap?.compress(compressFormat, 100, mOutputStream)
                    mOutputStream.flush()
                    mOutputStream.close()
                    ensureIsOnMainThread { callback(Result.success(finalDestination)) }
                } else {
                    ensureIsOnMainThread { callback(Result.failure(Throwable(mContext.getCompatString(R.string.const_bitmap_null_error)))) }
                }
            } catch (e: Exception) {
                ensureIsOnMainThread { callback(Result.failure(e)) }
            }
        } else {
            ensureIsOnMainThread { callback(Result.failure(Throwable(mContext.getCompatString(R.string.const_url_null_or_empty_error)))) }
        }
    }
}