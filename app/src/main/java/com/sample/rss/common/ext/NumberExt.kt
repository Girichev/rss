package com.sample.rss.common.ext

import java.text.SimpleDateFormat
import java.util.*

fun Long.formattedDateTime(): String {
    val date = Date(this)
    val formatter = SimpleDateFormat("EEE, dd MMM yyy HH:mm:ss", Locale.getDefault())
    formatter.timeZone = TimeZone.getDefault()
    return formatter.format(date)
}