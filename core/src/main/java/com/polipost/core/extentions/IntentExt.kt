package com.polipost.core.extentions

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment

inline fun <reified T> Context.launchActivity(block: Intent.() -> Unit) {
    val intent = Intent(this, T::class.java)
    intent.block()
    startActivity(intent)
}

inline fun <reified T> Fragment.launchActivity(block: Intent.() -> Unit) {
    val intent = Intent(requireContext(), T::class.java)
    intent.block()
    requireContext().startActivity(intent)
}

inline fun <reified T> Context.buildIntent(callback: Intent.() -> Unit): Intent {
    val mIntent = Intent(this, T::class.java)
    mIntent.callback()
    return mIntent
}