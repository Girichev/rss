package com.sample.rss.rss

import androidx.annotation.WorkerThread
import com.sample.rss.room.AppDB
import com.sample.rss.rss.model.RssChannel
import com.sample.rss.rss.model.RssFeed
import com.sample.rss.rss.model.RssItem
import javax.inject.Inject

/**
 * Convert and save feed into SQLite DataBase
 */
class RssStorage @Inject constructor(private val appDB: AppDB) {

    @WorkerThread
    fun saveFeed(vararg feed: RssFeed) {
        feed.forEach { saveFeed(it) }
    }

    @WorkerThread
    fun saveFeed(feed: RssFeed) {
        feed.channel?.let { channel ->
            saveChannel(channel)
            channel.items?.let { items -> saveItems(channel, items) }
        }
    }

    @WorkerThread
    private fun saveChannel(channel: RssChannel) {
        channel.toRoomEntity().let { channelEntity ->
            appDB.channelDao.insertOrUpdate(channelEntity)
        }
    }

    @WorkerThread
    private fun saveItems(channel: RssChannel, items: List<RssItem>) {
        items.map { it.toRoomEntity() }.forEach {
            appDB.itemDao.insertOrUpdate(it.apply {
                rssChannelTitle = channel.title.orEmpty()
            })
        }
    }
}

fun RssItem.toRoomEntity() = map(this)
fun RssChannel.toRoomEntity() = map(this)