package com.sample.rss.room.dao

import androidx.annotation.WorkerThread
import androidx.room.Dao
import androidx.room.Query
import com.sample.rss.room.BaseDao
import com.sample.rss.room.entity.RssChannelEntity
import timber.log.Timber

@Dao
interface RssChannelDao : BaseDao<RssChannelEntity> {

    @Query("SELECT * FROM rss_channel WHERE title = :title LIMIT 1")
    fun getOneByTitle(title: String): RssChannelEntity?

    @WorkerThread
    fun insertOrUpdate(entity: RssChannelEntity) {
        getOneByTitle(entity.link)?.let {
            if(it.equals(entity).not()) {
                update(entity)
            } else {
                Timber.d("Skip update content the same")
            }
        } ?: kotlin.run {
            insert(entity)
        }
    }
}