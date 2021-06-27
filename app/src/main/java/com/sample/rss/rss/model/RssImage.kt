package com.sample.rss.rss.model

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "image", strict = false)
data class RssImage(
    @field:Element(name = "url", required = true)
    @param:Element(name = "url", required = true)
    var url: String? = null,

    @field:Element(name = "title", required = false)
    @param:Element(name = "title", required = false)
    var title: String? = null,

    @field:Element(name = "link", required = false)
    @param:Element(name = "link", required = false)
    var link: String? = null
)