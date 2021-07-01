package com.sample.rss.room

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface BaseDao<T> {

    /**
     * Insert an array of objects in the database.
     *
     * @param obj the objects to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(vararg obj: T)

    /**
     * Insert an array of objects in the database.
     *
     * @param list the objects to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(list: List<T>)

    /**
     * Update an object from the database.
     *
     * @param obj the object to be updated
     */
    @Update
    suspend fun update(obj: T): Int

    /**
     * Delete an object from the database
     *
     * @param obj the object to be deleted
     */
    @Delete
    suspend fun delete(obj: T): Int
}