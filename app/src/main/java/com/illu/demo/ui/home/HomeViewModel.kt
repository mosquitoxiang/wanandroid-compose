package com.illu.demo.ui.home

import androidx.lifecycle.MutableLiveData
import com.illu.demo.base.BaseViewModel
import com.illu.demo.bean.ArticleBean
import com.illu.demo.common.UserManager
import com.illu.demo.common.bus.Bus
import com.illu.demo.common.bus.USER_COLLECT_UPDATE
import com.illu.demo.common.isLogin
import com.illu.demo.common.loadmore.LoadMoreStatus

class HotViewModel : BaseViewModel() {

    companion object {
        const val INITIAL_PAGE = 0
    }

    val refreshStatus = MutableLiveData<Boolean>()
    val reloadStatus = MutableLiveData<Boolean>()
    val loadMoreStatus = MutableLiveData<LoadMoreStatus>()
    val articleList = MutableLiveData<MutableList<ArticleBean>>()

    init {
        //放在这里处理第一页面可见，再次切换tab不会重复请求页面
        refreshArticleList()
    }

    private var page = INITIAL_PAGE

    fun refreshArticleList() {
        refreshStatus.value = true
        reloadStatus.value = false
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
                articleList.value = mutableListOf<ArticleBean>().apply {
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

    fun updateItemCollectState(target: Pair<Int, Boolean>) {
        val list = articleList.value
        val item = list?.find { it.id == target.first } ?: return
        item.collect = target.second
        articleList.value = list!!
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
        articleList.value = list!!
    }
}