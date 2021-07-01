package com.sample.rss.common.ext

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast

fun Activity.openUrl(link: String) {
    startActivity(Intent(Intent.ACTION_VIEW).apply { data = Uri.parse(link) })
}

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}