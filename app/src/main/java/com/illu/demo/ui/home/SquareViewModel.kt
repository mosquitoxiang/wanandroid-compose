package com.illu.demo.ui.home

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.toMutableStateList
import com.illu.demo.base.BaseViewModel
import com.illu.demo.bean.ArticleBean
import com.illu.demo.common.UserManager
import com.illu.demo.common.bus.Bus
import com.illu.demo.common.bus.USER_COLLECT_UPDATE
import com.illu.demo.common.isLogin
import kotlinx.coroutines.flow.MutableStateFlow

class SquareViewModel : BaseViewModel() {

    companion object {
        const val INITIAL_PAGE = 0
    }

    val refreshStatus = MutableStateFlow<Boolean?>(null)
    val articleList = MutableStateFlow<MutableList<ArticleBean>?>(null)

    private var page = INITIAL_PAGE

    init {
        getSquareData()
    }

    fun getSquareData() {
        refreshStatus.value = true
        launch(
            block = {
                val squareData = mRespository.getSquareData(INITIAL_PAGE)
                page = squareData.curPage
                articleList.value = squareData.datas.toMutableStateList()
                refreshStatus.value = false
            },
            error = {
                refreshStatus.value = false
            }
        )
    }

    fun loadMore() {
        launch(
            block = {
                val squareData = mRespository.getSquareData(page)
                page = squareData.curPage

                val currentList = articleList.value ?: mutableStateListOf()
                currentList.addAll(squareData.datas)
                articleList.value = currentList
            },
            error = {

            }
        )
    }

    fun collect(id: Int) {
        launch(
            block = {
                mRespository.collect(id)
                UserManager.addCollectId(id)
                updateItemCollectState(id to true)
                Bus.post(USER_COLLECT_UPDATE, id to true)
            },
            error = {
                updateItemCollectState(id to false)
            }
        )
    }

    fun uncollect(id: Int) {
        launch(
            block = {
                mRespository.unCollect(id)
                UserManager.removeCollectId(id)
                updateItemCollectState(id to false)
                Bus.post(USER_COLLECT_UPDATE, id to false)
            },
            error = {
                updateItemCollectState(id to true)
            }
        )
    }

    fun updateListCollectState() {
        val list = articleList.value
        if (list.isNullOrEmpty()) return
        if (isLogin()) {
            val collectIds = UserManager.getUserInfo()?.collectIds ?: return
            list.forEach { it.collect = collectIds.contains(it.id) }
        } else {
            list.forEach { it.collect = false }
        }
        articleList.value = list
    }

    fun updateItemCollectState(target: Pair<Int, Boolean>) {
        val list = articleList.value
        val item = list?.find { it.id == target.first } ?: return
        item.collect = target.second
        articleList.value = list
    }
}