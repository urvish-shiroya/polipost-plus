package com.polipost.core.extentions

import android.os.Looper
import kotlinx.coroutines.launch

val backgroundScope = kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.IO)

val mainScope = kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.Main)

fun checkIsMainThread(): Boolean {
    return Looper.myLooper() == Looper.getMainLooper()
}

fun ensureIsOnBackgroundThread(callback: () -> Unit) {
    if (checkIsMainThread()) {
        backgroundScope.launch { callback() }
    } else {
        callback()
    }
}

fun ensureIsOnMainThread(callback: () -> Unit) {
    if (checkIsMainThread()) {
        callback()
    } else {
        mainScope.launch { callback() }
    }
}