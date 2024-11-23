package com.polipost.core.extentions

import android.content.Context
import androidx.core.content.ContextCompat
import java.io.File

private fun Context.getBaseDirectory(): File {
    return filesDir
}

fun Context.getTemplatesDirectory(): File {
    return File(getBaseDirectory(), "templates")
}

fun Context.getProfilesDirectory(): File {
    return File(getBaseDirectory(), "Profiles")
}