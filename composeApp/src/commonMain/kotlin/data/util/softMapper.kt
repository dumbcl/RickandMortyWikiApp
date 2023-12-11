package data.util

fun String.httpsStringToId(): Int = this.split("/").last().toInt()