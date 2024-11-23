package com.polipost.core.extentions

fun Any?.isNotNull(): Boolean {
    return this != null
}

fun Any?.isNull(): Boolean {
    return this == null
}

fun Any?.ifNotNull(code: Any.() -> Unit) {
    if (this != null) code(this as Any)
}

fun Any?.ifNull(code: () -> Unit) {
    if (this == null) code()
}

fun Boolean?.ifTrue(code: () -> Unit) {
    if (this == true) code()
}

fun Boolean?.ifFalse(code: () -> Unit) {
    if (this == false) code()
}

fun Boolean?.isTrue() = this == true

fun Boolean?.isFalse() = this == false