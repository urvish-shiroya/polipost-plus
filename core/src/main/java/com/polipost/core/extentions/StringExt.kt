package com.polipost.core.extentions

fun checkStringValue(value: String?): Boolean {
    return !value.isNullOrBlank() && value != "null"
}

fun String?.capitalizeFirstLetter(): String {
    return if (checkStringValue(this)) {
        this?.get(0)?.uppercase() + this?.substring(1)?.lowercase()
    } else {
        this ?: ""
    }
}