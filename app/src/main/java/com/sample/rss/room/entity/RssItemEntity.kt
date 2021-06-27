package com.sample.rss.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rss_item")
data class RssItemEntity(
    @PrimaryKey
    val guid: String = "",
    var link: String = "",
    var title: String = "",
    var description: String = "",
    var timestamp: Long = 0L,
    var rssChannelTitle: String = ""
) {
    var isBlocked: Boolean = false
    var isViewed: Boolean = false
}