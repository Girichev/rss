package com.sample.rss.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.sample.rss.common.ext.ioThread
import com.sample.rss.room.dao.RssChannelDao
import com.sample.rss.room.dao.RssItemDao
import com.sample.rss.room.entity.RssChannelEntity
import com.sample.rss.room.entity.RssItemEntity
import com.sample.rss.room.view.RssItemView

@Database(
    entities = [RssItemEntity::class, RssChannelEntity::class],
    views = [RssItemView::class],
    version = 1,
    exportSchema = false
)
abstract class AppDB : RoomDatabase() {

    abstract val itemDao: RssItemDao
    abstract val channelDao: RssChannelDao

    companion object {
        @Volatile
        private var INSTANCE: AppDB? = null

        fun getInstance(context: Context): AppDB =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDB::class.java,
                "rss.db"
            ).addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    ioThread { fillInitialData(getInstance(context)) }
                }
            }).build()

        private fun fillInitialData(appDataBase: AppDB) {
            // @TODO Fill in the initial data here
        }
    }
}