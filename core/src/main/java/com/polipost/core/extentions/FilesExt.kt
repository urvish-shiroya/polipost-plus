package com.polipost.core.extentions

import java.io.File

fun File?.checkIsExists(): Boolean {
    return try {
        val mFile = this ?: return false
        mFile.exists()
    } catch (_: Exception) {
        false
    }
}

fun String?.asFile(): File? {
    return try {
        if (checkStringValue(this)) {
            File(this.toString())
        } else null
    } catch (_: Exception) {
        null
    }
}

fun File?.createDirectory(): Boolean {
    return try {
        val mFile = this ?: return false
        if (!mFile.checkIsExists()) {
            mFile.mkdirs()
        } else true
    } catch (_: Exception) {
        false
    }
}