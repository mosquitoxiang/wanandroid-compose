package com.illu.demo.common.room

import androidx.room.Room
import com.illu.baselibrary.App

object RoomHelper {

    private val appDatabase by lazy {
        Room.databaseBuilder(App.INSTANCE, AppDatabase::class.java, "database_wanandroid").build()
    }

    private val historyDao by lazy { appDatabase.history() }

    suspend fun queryHistory() = historyDao.queryAll()

    suspend fun addHistory(history: History) = historyDao.insert(history)

    suspend fun delete(content: String) = historyDao.delete(content)
}