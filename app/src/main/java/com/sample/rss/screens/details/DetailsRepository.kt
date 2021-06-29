package com.sample.rss.screens.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.sample.rss.room.AppDB
import com.sample.rss.room.entity.RssItemEntity
import com.sample.rss.room.view.RssItemView
import javax.inject.Inject

class DetailsRepository @Inject constructor(private val appDB: AppDB) {

    private val guid: MutableLiveData<String> by lazy {
        MutableLiveData()
    }

    val rssItem: LiveData<RssItemView> by lazy {
        Transformations.switchMap(guid) { id -> appDB.itemDao.getOneByGuidLiveData(id) }
    }

    fun setGuid(guid: String) {
        this.guid.postValue(guid)
    }

    suspend fun markAsViewed(item: RssItemView): Boolean {
        return appDB.itemDao.update(item.toRoomEntity().apply { isViewed = true }) > 0
    }

    suspend fun markAsDeleted(item: RssItemView): Boolean {
        return appDB.itemDao.update(item.toRoomEntity().apply { isBlocked = true }) > 0
    }
}

fun RssItemView.toRoomEntity(): RssItemEntity = map(this)
