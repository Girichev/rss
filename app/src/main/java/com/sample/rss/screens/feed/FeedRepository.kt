package com.sample.rss.screens.feed

import androidx.lifecycle.LiveData
import com.sample.rss.room.AppDB
import com.sample.rss.room.toRoomSearch
import com.sample.rss.room.view.RssItemView
import javax.inject.Inject

class FeedRepository @Inject constructor(private val appDB: AppDB) {
    fun getFeed(query: String): LiveData<List<RssItemView>> = appDB.itemDao.getAll(query.toRoomSearch())
}