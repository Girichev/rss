package com.sample.rss.rss

import com.sample.rss.room.AppDB
import com.sample.rss.rss.model.RssChannel
import com.sample.rss.rss.model.RssFeed
import com.sample.rss.rss.model.RssItem
import javax.inject.Inject

/**
 * Convert and save feed into SQLite DataBase
 */
class RssStorage @Inject constructor(private val appDB: AppDB) {

    suspend fun save(vararg feed: RssFeed) {
        feed.forEach { saveFeed(it) }
    }

    private suspend fun saveFeed(feed: RssFeed) {
        feed.channel?.let { channel ->
            saveChannel(channel)
            channel.items?.let { items -> saveItems(channel, items) }
        }
    }

    private suspend fun saveChannel(channel: RssChannel) {
        channel.toRoomEntity().let { channelEntity ->
            appDB.channelDao.insertOrUpdate(channelEntity)
        }
    }

    private suspend fun saveItems(channel: RssChannel, items: List<RssItem>) {
        items.map { it.toRoomEntity() }.forEach {
            appDB.itemDao.insertOrUpdate(it.apply {
                rssChannelTitle = channel.title.orEmpty()
            })
        }
    }
}

fun RssItem.toRoomEntity() = map(this)
fun RssChannel.toRoomEntity() = map(this)