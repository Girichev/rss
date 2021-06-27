package com.sample.rss.room.view

import androidx.room.DatabaseView
import com.sample.rss.common.base.BaseMapper
import com.sample.rss.common.ext.formattedDateTime
import com.sample.rss.room.entity.RssItemEntity

@DatabaseView("SELECT I.*, C.link AS channelLink FROM rss_item I LEFT JOIN rss_channel C ON (I.rssChannelTitle == C.title) GROUP BY I.guid")
data class RssItemView(
    var guid: String = "",
    var link: String = "",
    var title: String = "",
    var description: String = "",
    var timestamp: Long = 0L,
    var rssChannelTitle: String = "",
    var isBlocked: Boolean = false,
    var isViewed: Boolean = false,
    val channelLink: String = ""
) : BaseMapper<RssItemView, RssItemEntity> {

    val formattedDateTime get() = timestamp.formattedDateTime()

    override fun map(src: RssItemView): RssItemEntity {
        return RssItemEntity(
            guid = src.guid.trim(),
            link = src.link.trim(),
            title = src.title.trim(),
            description = src.description.trim(),
            timestamp = src.timestamp,
            rssChannelTitle = src.rssChannelTitle
        ).apply {
            isBlocked = src.isBlocked
            isViewed = src.isViewed
        }
    }
}