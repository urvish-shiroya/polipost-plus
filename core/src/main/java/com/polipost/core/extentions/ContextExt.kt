package com.polipost.core.extentions

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.provider.Settings
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

fun Context?.isNetworkConnected(): Boolean {
    val connectivityManager = this?.let {
        ContextCompat.getSystemService(it, ConnectivityManager::class.java)
    } ?: return false

    val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        ?: return false

    return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
}

@SuppressLint("HardwareIds")
fun Context.getSettingId(): String {
    return Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID)
}

fun Context?.showToast(@StringRes resId: Int) {
    this?.let {
        ensureIsOnMainThread {
            Toast.makeText(it, it.getCompatString(resId), Toast.LENGTH_SHORT).show()
        }
    }
}

fun Context?.showToast(message: String?) {
    if (checkStringValue(message)) {
        this?.let {
            ensureIsOnMainThread {
                Toast.makeText(it, message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}