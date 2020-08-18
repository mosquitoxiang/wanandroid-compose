package com.illu.demo.ui.home.project

import androidx.lifecycle.MutableLiveData
import com.illu.demo.base.BaseViewModel
import com.illu.demo.bean.ArticleBean
import com.illu.demo.common.UserManager
import com.illu.demo.common.bus.Bus
import com.illu.demo.common.bus.USER_COLLECT_UPDATE
import com.illu.demo.common.isLogin
import com.illu.demo.common.loadmore.LoadMoreStatus

class ProjectViewModel : BaseViewModel() {

    companion object {
        const val INITIAL_PAGE = 1
        const val INITIAL_CHECKED = 0
    }

    val loadMoreStatus = MutableLiveData<LoadMoreStatus>()
    val refreshStatus = MutableLiveData<Boolean>()
    val reloadStatus = MutableLiveData<Boolean>()
    val treeListLiveData = MutableLiveData<MutableList<CategoryBean>>()
    val articleList = MutableLiveData<MutableList<ArticleBean>>()
    val checkPositionLiveData = MutableLiveData<Int>()

    private var page = INITIAL_PAGE

    fun getTreeData() {
        refreshStatus.value = true
        reloadStatus.value = false
        launch(
            block = {
                val responseList = mRespository.getTreeData()
                treeListLiveData.value = responseList.toMutableList()
                val id = responseList[INITIAL_CHECKED].id
                checkPositionLiveData.value = INITIAL_CHECKED
                val childrenData = mRespository.getChildrenData(INITIAL_PAGE, id)
                page = childrenData.curPage
                articleList.value = childrenData.datas.toMutableList()
                refreshStatus.value = false
            },
            error = {
                refreshStatus.value = false
                reloadStatus.value = true
            }
        )
    }

    fun loadMoreData() {
        loadMoreStatus.value = LoadMoreStatus.LOADING
        launch(
            block = {
                val treeList = treeListLiveData.value ?:return@launch
                val checkPosition = checkPositionLiveData.value ?: return@launch
                val id = treeList[checkPosition].id
                val newChildData = mRespository.getChildrenData(page + 1, id)
                page = newChildData.curPage
                val currentList = articleList.value ?: mutableListOf()
                currentList.addAll(newChildData.datas)
                articleList.value = currentList
                loadMoreStatus.value = if (newChildData.offset >= newChildData.total) {
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

    fun changePosition(position: Int = checkPositionLiveData.value ?: INITIAL_CHECKED) {
        refreshStatus.value = true
        reloadStatus.value = false
        if (position != checkPositionLiveData.value) {
            articleList.value = mutableListOf()
            checkPositionLiveData.value = position
        }
        launch(
            block = {
                val treeList = treeListLiveData.value ?: return@launch
                val id = treeList[position].id
                val childrenData = mRespository.getChildrenData(INITIAL_PAGE, id)
                page = childrenData.curPage
                articleList.value = childrenData.datas.toMutableList()

                refreshStatus.value = false
            },
            error = {
                refreshStatus.value = false
                reloadStatus.value = true
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