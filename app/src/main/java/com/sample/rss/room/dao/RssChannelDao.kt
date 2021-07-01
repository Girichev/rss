package com.sample.rss.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.sample.rss.room.BaseDao
import com.sample.rss.room.entity.RssChannelEntity

@Dao
interface RssChannelDao : BaseDao<RssChannelEntity> {

    @Query("SELECT * FROM rss_channel WHERE title = :title LIMIT 1")
    suspend fun getOneByTitle(title: String): RssChannelEntity?

    @Transaction
    suspend fun insertOrUpdate(entity: RssChannelEntity) {
        getOneByTitle(entity.link)?.let {
            if(it.equals(entity).not()) { update(entity) }
        } ?: insert(entity)
    }
}