package com.sample.rss.rss.model

import com.sample.rss.common.base.BaseMapper
import com.sample.rss.common.ext.toTimestamp
import com.sample.rss.room.entity.RssItemEntity
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "item", strict = false)
data class RssItem(

    @field:Element(name = "title", required = true)
    @param:Element(name = "title", required = true)
    var title: String? = null,

    @field:Element(name = "link", required = true)
    @param:Element(name = "link", required = true)
    var link: String? = null,

    @field:Element(name = "guid", required = true)
    @param:Element(name = "guid", required = true)
    var guid: String? = null,

    @field:Element(name = "pubDate", required = false)
    @param:Element(name = "pubDate", required = false)
    var pubDate: String? = null,

    @field:Element(name = "description", required = false)
    @param:Element(name = "description", required = false)
    var description: String? = null
) : BaseMapper<RssItem, RssItemEntity> {

    override fun map(src: RssItem): RssItemEntity {
        return RssItemEntity(
            guid = src.guid.orEmpty().trim(),
            link = src.link.orEmpty().trim(),
            title = src.title.orEmpty().trim(),
            description = src.description.orEmpty().trim(),
            timestamp = src.pubDate?.toTimestamp() ?: 0L
        )
    }
}