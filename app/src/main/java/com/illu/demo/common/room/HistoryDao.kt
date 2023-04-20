package com.illu.demo.common.room

import androidx.room.*

@Dao
interface HistoryDao {

    @Insert(entity = History::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(history: History)

    @Query("SELECT * FROM history order by id desc")
    suspend fun queryAll(): List<History>

    @Query("delete from History where searchWord = :content")
    suspend fun delete(content: String)
}