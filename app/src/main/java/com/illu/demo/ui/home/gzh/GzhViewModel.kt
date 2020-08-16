package com.illu.demo.ui.home.gzh

import androidx.lifecycle.MutableLiveData
import com.chad.library.adapter.base.loadmore.LoadMoreView
import com.illu.demo.base.BaseViewModel
import com.illu.demo.bean.ArticleBean
import com.illu.demo.bean.PageBean
import com.illu.demo.common.UserManager
import com.illu.demo.common.bus.Bus
import com.illu.demo.common.bus.USER_COLLECT_UPDATE
import com.illu.demo.common.isLogin
import com.illu.demo.common.loadmore.LoadMoreStatus
import com.illu.demo.net.HttpUtils
import com.illu.demo.ui.home.project.ProjectBean

class GzhViewModel : BaseViewModel() {

    companion object {
        const val INITIAL_PAGE = 1
        const val CHECKED_POSITION = 0
    }

    val freshStatus = MutableLiveData<Boolean>()
    val reloadStatus = MutableLiveData<Boolean>()
    val checkedPosition = MutableLiveData<Int>()
    val loadingMoreStatus = MutableLiveData<LoadMoreStatus>()
    val authorList = MutableLiveData<MutableList<ProjectBean>>()
    val articleList = MutableLiveData<MutableList<ArticleBean>>()

    private var page = INITIAL_PAGE

    fun requestData() {
        freshStatus.value = true
        reloadStatus.value = false
        launch(
            block = {
                val author = mRespository.getGzhName().toMutableList()
                authorList.value = author
                val id = author[CHECKED_POSITION].id
                val article = mRespository.getGzhArticle(id, page)
                articleList.value = article.datas.toMutableList()
                checkedPosition.value = CHECKED_POSITION
                page = article.curPage
                freshStatus.value = false
            },
            error = {
                freshStatus.value = false
                reloadStatus.value = true
            }
        )
    }

    fun changeData(checkPosition: Int = checkedPosition.value ?: CHECKED_POSITION) {
        freshStatus.value = true
        reloadStatus.value = false
        if (checkPosition != checkedPosition.value) {
            articleList.value = mutableListOf()
            checkedPosition.value = checkPosition
        }
        launch(
            block = {
                val author = authorList.value ?: return@launch
                val id = author[checkPosition].id
                val article = mRespository.getGzhArticle(id, INITIAL_PAGE)
                articleList.value = article.datas.toMutableList()
                freshStatus.value = false
            },
            error = {
                freshStatus.value = false
                reloadStatus.value = true
            }
        )
    }

    fun loadMoreData() {
        loadingMoreStatus.value = LoadMoreStatus.LOADING
        launch(
            block = {
                val author = authorList.value ?: return@launch
                val checkedPosition = checkedPosition.value ?: return@launch
                val id = author[checkedPosition].id
                val article = mRespository.getGzhArticle(id, page + 1)
                val currentList = articleList.value ?: mutableListOf()
                currentList.addAll(article.datas)
                page = article.curPage
                loadingMoreStatus.value = if (article.offset >= article.total) {
                    LoadMoreStatus.END
                } else{
                    LoadMoreStatus.COMPLETED
                }
            },
            error = {
                loadingMoreStatus.value = LoadMoreStatus.ERROR
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

    fun unCollect(id: Int) {
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

    fun updateItemCollectState(target: Pair<Int, Boolean>) {
        val list = articleList.value
        val item = list?.find { it.id == target.first } ?: return
        item.collect = target.second
        articleList.value = list
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
}