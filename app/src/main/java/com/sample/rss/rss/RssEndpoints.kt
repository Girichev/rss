package com.sample.rss.rss

import com.sample.rss.rss.model.RssFeed
import io.reactivex.rxjava3.core.Observable

import retrofit2.http.GET

const val BASE_RSS_URL = "http://feeds.bbci.co.uk/"

interface RssEndpoints {
    @GET("news/rss.xml?edition=uk")
    fun getFeedHome(): Observable<RssFeed>

    @GET("news/world/rss.xml?edition=uk")
    fun getFeedWorld(): Observable<RssFeed>

    @GET("news/england/rss.xml?edition=uk")
    fun getFeedEngland(): Observable<RssFeed>
}