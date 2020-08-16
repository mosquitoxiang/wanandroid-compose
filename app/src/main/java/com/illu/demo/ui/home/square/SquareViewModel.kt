package com.illu.demo.ui.home.square

import androidx.lifecycle.MutableLiveData
import com.illu.demo.base.BaseViewModel
import com.illu.demo.bean.ArticleBean
import com.illu.demo.common.UserManager
import com.illu.demo.common.bus.Bus
import com.illu.demo.common.bus.USER_COLLECT_UPDATE
import com.illu.demo.common.isLogin
import com.illu.demo.common.loadmore.LoadMoreStatus

class SquareViewModel : BaseViewModel() {

    companion object {
        const val INITIAL_PAGE = 0
    }

    val refreshStatus = MutableLiveData<Boolean>()
    val reloadStatus = MutableLiveData<Boolean>()
    val articleList = MutableLiveData<MutableList<ArticleBean>>()
    val loadMoreStatus = MutableLiveData<LoadMoreStatus>()

    private var page = INITIAL_PAGE

    fun getSquareData() {
        reloadStatus.value = false
        refreshStatus.value = true
        launch(
            block = {
                val squareData = mRespository.getSquareData(INITIAL_PAGE)
                page = squareData.curPage
                articleList.value = squareData.datas.toMutableList()
                refreshStatus.value = false
            },
            error = {
                refreshStatus.value = false
                reloadStatus.value = page == INITIAL_PAGE
            }
        )
    }

    fun loadMore() {
        loadMoreStatus.value = LoadMoreStatus.LOADING
        launch(
            block = {
                val squareData = mRespository.getSquareData(page)
                page = squareData.curPage

                val currentList = articleList.value ?: mutableListOf()
                currentList.addAll(squareData.datas)
                articleList.value = currentList
                loadMoreStatus.value = if (squareData.offset >= squareData.total) {
                    LoadMoreStatus.END
                } else {
                    LoadMoreStatus.COMPLETED
                }
            },
            error = {
                loadMoreStatus.value = LoadMoreStatus.ERROR
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