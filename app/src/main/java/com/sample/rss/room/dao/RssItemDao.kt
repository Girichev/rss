package com.sample.rss.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.sample.rss.room.BaseDao
import com.sample.rss.room.entity.RssItemEntity
import com.sample.rss.room.view.RssItemView

@Dao
interface RssItemDao : BaseDao<RssItemEntity> {

    @Query("SELECT * FROM RssItemView WHERE guid = :guid LIMIT 1")
    suspend fun getOneByGuid(guid: String): RssItemView?

    @Query("SELECT * FROM RssItemView WHERE guid = :guid LIMIT 1")
    fun getOneByGuidLiveData(guid: String): LiveData<RssItemView>

    @Query("SELECT * FROM RssItemView WHERE isBlocked < 1 AND title LIKE :query ORDER BY timestamp DESC")
    fun getAll(query: String = "%"): LiveData<List<RssItemView>>

    @Transaction
    suspend fun insertOrUpdate(entity: RssItemEntity) {
        getOneByGuid(entity.guid)?.let {
            if(it.equals(entity).not()) {
                update(entity.apply {
                    isBlocked = it.isBlocked
                    isViewed = it.isViewed
                })
            }
        } ?: insert(entity)
    }
}