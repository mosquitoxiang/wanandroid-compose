package com.illu.demo.ui.home.search.history

import androidx.lifecycle.MutableLiveData
import com.illu.demo.base.BaseViewModel
import com.illu.demo.common.room.History
import com.illu.demo.common.room.RoomHelper
import com.illu.demo.ui.find.HotKey

class HistoryViewModel : BaseViewModel() {

    val hotKeyList = MutableLiveData<MutableList<HotKey>>()
    val historyList = MutableLiveData<MutableList<History>>()

    fun getHotSearch() {
        launch(
            block = {
                val hotKey = mRespository.getHotKey()
                hotKeyList.value = hotKey
            }
        )
    }

    fun getHistory() {
        launch(
            block = {
                val history = mRespository.getHistory()
                historyList.value = history.toMutableList()
            }
        )
    }

    fun addHistory(content: String) {
        val isContain = historyList.value?.any { it.searchWord?.contains(content)!! }
        if (isContain!!) return
        val history = History(content)
        launch(
            block = {
                mRespository.addHistory(history)
                historyList.value = mRespository.getHistory().toMutableList()
            }
        )
    }

    fun deleteHistory(content: String) {
        launch(
            block = {
                mRespository.deleteHistory(content)
                historyList.value = mRespository.getHistory().toMutableList()
            }
        )
    }
}