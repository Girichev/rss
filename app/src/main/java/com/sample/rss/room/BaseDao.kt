package com.sample.rss.room

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import io.reactivex.rxjava3.core.Single

interface BaseDao<T> {

    /**
     * Insert an array of objects in the database.
     *
     * @param obj the objects to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(vararg obj: T)

    /**
     * Insert an array of objects in the database.
     *
     * @param list the objects to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(list: List<T>)

    /**
     * Update an object from the database.
     *
     * @param obj the object to be updated
     */
    @Update
    fun update(obj: T): Int

    /**
     * Update an object from the database.
     *
     * @param obj the object to be updated
     */
    @Update
    fun updateAsync(obj: T): Single<Int>

    /**
     * Delete an object from the database
     *
     * @param obj the object to be deleted
     */
    @Delete
    fun deleteAsync(obj: T): Single<Int>
}