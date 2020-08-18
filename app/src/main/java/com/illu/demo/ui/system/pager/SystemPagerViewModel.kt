package com.illu.demo.ui.system.pager

import androidx.lifecycle.MutableLiveData
import com.illu.demo.base.BaseViewModel
import com.illu.demo.bean.ArticleBean
import com.illu.demo.common.loadmore.LoadMoreStatus

class SystemPagerViewModel : BaseViewModel() {

    companion object {
        const val INITIAL_PAGE = 0
    }

    val refreshStatus = MutableLiveData<Boolean>()
    val loadMoreStatus = MutableLiveData<LoadMoreStatus>()
    val reloadStatus = MutableLiveData<Boolean>()
    val articleList = MutableLiveData<MutableList<ArticleBean>>()

    private var page = INITIAL_PAGE
    private var id = -1

    fun changePosition(cid: Int) {
        if (cid != id) {
            id = cid
            articleList.value = mutableListOf()
        }
        refreshStatus.value = true
        reloadStatus.value = false
        launch(
            block = {
                val pagination = mRespository.getSystemArticle(INITIAL_PAGE, cid)
                articleList.value = pagination.datas.toMutableList()
                page = pagination.curPage
                refreshStatus.value = false
            },
            error = {
                refreshStatus.value = false
                reloadStatus.value = articleList.value?.isEmpty()
            }
        )
    }

    fun loadMore(cid: Int) {
        loadMoreStatus.value = LoadMoreStatus.LOADING
        launch(
            block = {
                val pagination = mRespository.getSystemArticle(page, cid)
                page = pagination.curPage
                val currentList = articleList.value ?: mutableListOf()
                currentList.addAll(pagination.datas)
                articleList.value = currentList
                loadMoreStatus.value = if (pagination.offset >= pagination.total) {
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

}