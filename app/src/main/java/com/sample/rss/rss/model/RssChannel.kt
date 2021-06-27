package com.sample.rss.rss.model

import com.sample.rss.common.base.BaseMapper
import com.sample.rss.room.entity.RssChannelEntity
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "channel", strict = false)
data class RssChannel(
    @field:Element(name = "link", required = true)
    @param:Element(name = "link", required = true)
    var link: String? = null,

    @field:Element(name = "title", required = true)
    @param:Element(name = "title", required = true)
    var title: String? = null,

    @field:Element(name = "description", required = false)
    @param:Element(name = "description", required = false)
    var description: String? = null,

    @field:Element(name = "image", required = false)
    @param:Element(name = "image", required = false)
    var image: RssImage? = null,

    @field:ElementList(name = "item", required = true, inline = true)
    @param:ElementList(name = "item", required = true, inline = true)
    var items: ArrayList<RssItem>? = null
) : BaseMapper<RssChannel, RssChannelEntity> {
    override fun map(src: RssChannel): RssChannelEntity {
        return RssChannelEntity(
            link = src.link.orEmpty().trim(),
            title = src.title.orEmpty().trim(),
            description = src.description.orEmpty().trim()
        )
    }
}