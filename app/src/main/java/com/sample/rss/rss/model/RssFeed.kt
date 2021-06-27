package com.sample.rss.rss.model

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "rss", strict = false)
data class RssFeed(

    @field:Element(name = "channel", required = false)
    @param:Element(name = "channel", required = false)
    var channel: RssChannel? = null
)