package com.polendina.kabular.utils

fun String.isNotEmptyNorBlank(): Boolean = isNotEmpty() || isNotBlank()

// TODO: I think this extension should be tested out. Kinda sussy
fun String.initialCapital(): String = if (isEmpty()) "" else first().uppercase().plus(substring(1 until length).lowercase())