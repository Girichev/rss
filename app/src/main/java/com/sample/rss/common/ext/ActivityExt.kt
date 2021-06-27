package com.sample.rss.common.ext

import android.app.Activity
import android.content.Intent
import android.net.Uri

fun Activity.openUrl(link: String) {
    startActivity(Intent(Intent.ACTION_VIEW).apply { data = Uri.parse(link) })
}