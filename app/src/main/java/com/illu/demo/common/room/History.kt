package com.illu.demo.common.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class History(var searchWord: String? = "") {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}