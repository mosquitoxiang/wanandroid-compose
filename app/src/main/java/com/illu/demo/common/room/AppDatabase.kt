package com.illu.demo.common.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [History::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun history(): HistoryDao
}