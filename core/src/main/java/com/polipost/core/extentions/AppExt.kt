package com.polipost.core.extentions

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Environment
import android.provider.Settings
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.annotation.Keep
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.polipost.core.R
import com.polipost.core.network.responses.GetUserProfileResponse
import com.polipost.core.store.preference.PreferenceHelper
import java.io.File

@Keep
val gson = Gson()

@Keep
inline fun <reified T> T.toJson(): String = gson.toJson(this)

@Keep
inline fun <reified T> String.to(): T = gson.fromJson<T>(this, object : TypeToken<T>() {}.type)

@Keep
inline fun <reified T> String.toAny(cls: Class<T>): T = gson.fromJson<T>(this, cls)

@Keep
inline fun <reified T : Any> String?.convertTo(): T? {
    if (checkStringValue(this)) {
        return Gson().fromJson(this.toString(), T::class.java) as? T?
    } else {
        return null
    }
}

@SuppressLint("HardwareIds")
internal fun Context.androidId(): String? {
    return Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
}

fun isTestMode(): Boolean {
    return "${Environment.getExternalStorageDirectory()}/TEST".asFile().checkIsExists()
}

fun <T> List<T>.isPositionValid(position: Int): Boolean {
    return position in this.indices
}

fun Activity.checkActivityAvailable(): Boolean {
    return !isFinishing && !isDestroyed
}

fun copyTextToClipboard(context: Context, string: String) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("Polipost", string)
    clipboard.setPrimaryClip(clip)
}

fun EditText.hideKeyboard() {
    requestFocus()
    val imm = ContextCompat.getSystemService(context, InputMethodManager::class.java)
    imm?.hideSoftInputFromWindow(windowToken, 0)
}