package com.sample.rss.room.dao

import androidx.annotation.WorkerThread
import androidx.room.Dao
import androidx.room.Query
import androidx.room.rxjava3.EmptyResultSetException
import com.sample.rss.room.BaseDao
import com.sample.rss.room.entity.RssChannelEntity
import com.sample.rss.room.entity.RssItemEntity
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

@Dao
interface RssChannelDao : BaseDao<RssChannelEntity> {

    @Query("SELECT * FROM rss_item WHERE title = :title LIMIT 1")
    fun getOneByTitle(title: String): Single<RssItemEntity>

    @WorkerThread
    fun insertOrUpdate(entity: RssChannelEntity): Disposable {
        return getOneByTitle(entity.link).subscribeOn(Schedulers.io())
            .subscribeBy({
                it.printStackTrace()
                if(it is EmptyResultSetException) {
                    insert(entity)
                }
            }, {
                if(it.equals(entity).not()) {
                    // Need update
                    update(entity)
                } else {
                    // Content the same
                    Timber.d("Skip update content the same")
                }
            })
    }
}