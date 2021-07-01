package com.sample.rss.rss

import com.sample.rss.rss.model.RssFeed
import retrofit2.http.GET

const val BASE_RSS_URL = "http://feeds.bbci.co.uk/"

interface RssEndpoints {
    @GET("news/rss.xml?edition=uk")
    suspend fun getFeedHome(): RssFeed

    @GET("news/world/rss.xml?edition=uk")
    suspend fun getFeedWorld(): RssFeed

    @GET("news/england/rss.xml?edition=uk")
    suspend fun getFeedEngland(): RssFeed
}