package com.sample.rss.screens.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.sample.rss.room.AppDB
import com.sample.rss.room.entity.RssItemEntity
import com.sample.rss.room.view.RssItemView
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class DetailsRepository @Inject constructor(private val appDB: AppDB) {

    private val guid: MutableLiveData<String> by lazy {
        MutableLiveData()
    }

    val item: LiveData<RssItemView> by lazy {
        Transformations.switchMap(guid) { lnk -> appDB.itemDao.getOneByGuidLD(lnk) }
    }

    fun setLink(guid: String) {
        this.guid.postValue(guid)
    }

    fun setViewed(): Single<Int>? {
        return item.value?.let {
            appDB.itemDao.updateAsync(it.toRoomEntity().apply { isViewed = true })
        }
    }

    fun markAsDeleted(): Single<Int>? {
        return item.value?.let {
            appDB.itemDao.updateAsync(it.toRoomEntity().apply { isBlocked = true })
        }
    }
}

fun RssItemView.toRoomEntity(): RssItemEntity = map(this)
