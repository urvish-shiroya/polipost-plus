package com.polipost.core.extentions

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

fun Context.getCompatString(@StringRes resId: Int): String {
    return ContextCompat.getString(this, resId)
}

fun Context.getCompatColor(@ColorRes resId: Int): Int {
    return ContextCompat.getColor(this, resId)
}

fun Context.getCompatDrawable(@DrawableRes resId: Int): Drawable {
    return ContextCompat.getDrawable(this, resId)!!
}