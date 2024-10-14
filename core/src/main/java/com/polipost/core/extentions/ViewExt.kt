package com.polipost.core.extentions

import android.view.View

fun View.visible() = kotlin.run {
    this.visibility = View.VISIBLE
}

fun View.gone() = kotlin.run {
    this.visibility = View.GONE
}

fun View.invisible() = kotlin.run {
    this.visibility = View.INVISIBLE
}

fun View.visibleIf(condition: Boolean) = kotlin.run {
    this.visibility = if (condition) View.VISIBLE else View.GONE
}

fun View.goneIf(condition: Boolean) = kotlin.run {
    this.visibility = if (condition) View.GONE else View.VISIBLE
}

fun View?.setOnSafeClickListener(interval: Long = 500L, onSafeClick: (View) -> Unit) {
    var lastClickTime = 0L
    this?.setOnClickListener {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastClickTime >= interval) {
            lastClickTime = currentTime
            onSafeClick(it)
        }
    }
}