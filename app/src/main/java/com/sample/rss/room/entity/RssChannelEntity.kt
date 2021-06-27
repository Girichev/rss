package com.sample.rss.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "rss_channel"
)
data class RssChannelEntity(
    @PrimaryKey
    var title: String = "",
    var description: String = "",
    var link: String = ""
)