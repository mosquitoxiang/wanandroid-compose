package com.illu.demo.ui.home.hot

import androidx.lifecycle.MutableLiveData
import com.illu.demo.base.BaseViewModel
import com.illu.demo.common.loadmore.LoadMoreStatus

class HotViewModel : BaseViewModel() {

    companion object {
        const val INITIAL_PAGE = 0
    }

    val refreshStatus = MutableLiveData<Boolean>()
    val reloadStatus = MutableLiveData<Boolean>()
    val loadMoreStatus = MutableLiveData<LoadMoreStatus>()
    val articleList: MutableLiveData<MutableList<HotBean>> = MutableLiveData()

    private var page = INITIAL_PAGE

    fun refreshArticlelist() {
        refreshStatus.value = true
        launch(
            block = {
                val topArticleDefferd = async {
                    mRespository.getTopArticleList()
                }
                val pageDefferd = async {
                    mRespository.getArticleList(INITIAL_PAGE)
                }
                val topArticleList = topArticleDefferd.await().apply { forEach { it.top = true } }
                val pagination = pageDefferd.await()
                page = pagination.curPage
                articleList.value = mutableListOf<HotBean>().apply {
                    addAll(topArticleList)
                    addAll(pagination.datas)
                }
                refreshStatus.value = false
            },
            error = {
                refreshStatus.value = false
                reloadStatus.value = page == INITIAL_PAGE
            }
        )
    }

    fun loadMoreArticleList() {
        loadMoreStatus.value = LoadMoreStatus.LOADING
        launch(
            block = {
                val pagination = mRespository.getArticleList(page)
                page = pagination.curPage
                val currenList = articleList.value ?: mutableListOf()
                currenList.addAll(pagination.datas)
                articleList.value = currenList
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

    fun uncollect(id: Int) {

    }

    fun collect(id: Int) {

    }
}