package com.polipost.core.extentions

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.view.View
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.P)
val isAndroid9 = kotlin.run { Build.VERSION.SDK_INT >= Build.VERSION_CODES.P }

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.Q)
val isAndroid10 = kotlin.run { Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q }

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.R)
val isAndroid11 = kotlin.run { Build.VERSION.SDK_INT >= Build.VERSION_CODES.R }

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S)
val isAndroid12 = kotlin.run { Build.VERSION.SDK_INT >= Build.VERSION_CODES.S }

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.TIRAMISU)
val isAndroid13 = kotlin.run { Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU }

fun Context?.getDeviceWidth(): Int {
    return this?.resources?.displayMetrics?.widthPixels ?: 0
}

fun Context?.getDeviceHeight(): Int {
    return this?.resources?.displayMetrics?.heightPixels ?: 0
}

fun AppCompatActivity.changeStatusBarColor(@ColorRes color: Int) {
    window.statusBarColor = getCompatColor(color)
}

fun Context?.isPortrait(): Boolean {
    return this?.resources?.configuration?.orientation == Configuration.ORIENTATION_PORTRAIT
}

fun AppCompatActivity?.setSystemBarInsets(mView: View, left: Int? = null, top: Int? = null, right: Int? = null, bottom: Int? = null) {
    ViewCompat.setOnApplyWindowInsetsListener(mView) { v, insets ->
        val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        v.setPadding(left ?: systemBars.left, top ?: systemBars.top, right ?: systemBars.right, bottom ?: systemBars.bottom)
        insets
    }
}