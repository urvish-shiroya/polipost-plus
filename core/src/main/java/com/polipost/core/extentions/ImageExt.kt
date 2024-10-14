package com.polipost.core.extentions

import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide

fun AppCompatImageView?.loadImageFromUrl(imageUrl: String?, placeHolder: Int = 0) {
    val imageView = this ?: return
    if (checkStringValue(imageUrl)) {
        if (placeHolder != 0) {
            Glide.with(imageView.context).load(imageUrl).placeholder(placeHolder).error(placeHolder).into(imageView)
        } else {
            Glide.with(imageView.context).load(imageUrl).into(imageView)
        }
    }
}