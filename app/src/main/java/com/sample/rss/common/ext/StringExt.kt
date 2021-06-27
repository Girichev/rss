package com.sample.rss.common.ext

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun String.toTimestamp() = try {
    SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH).parse(this.trim())?.time ?: 0L
} catch (ex: ParseException) {
    0L
}