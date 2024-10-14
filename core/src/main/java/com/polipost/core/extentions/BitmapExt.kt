package com.polipost.core.extentions

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.view.View

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