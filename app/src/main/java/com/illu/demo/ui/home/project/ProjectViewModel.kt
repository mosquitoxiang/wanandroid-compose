package com.illu.demo.ui.home.project

import androidx.lifecycle.MutableLiveData
import com.illu.demo.base.BaseViewModel
import com.illu.demo.bean.ArticleBean
import com.illu.demo.common.loadmore.LoadMoreStatus
import com.illu.demo.net.HttpUtils

class ProjectViewModel : BaseViewModel() {

    companion object {
        const val INITIAL_PAGE = 1
        const val INITIAL_CHECKED = 0
    }

    val loadMoreStatus = MutableLiveData<LoadMoreStatus>()
    val refreshStatus = MutableLiveData<Boolean>()
    val reloadStatus = MutableLiveData<Boolean>()
    val treeListLiveData = MutableLiveData<MutableList<ProjectBean>>()
    val childListLiveData = MutableLiveData<MutableList<ArticleBean>>()
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
                childListLiveData.value = childrenData.datas.toMutableList()
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
                val currentList = childListLiveData.value ?: mutableListOf()
                currentList.addAll(newChildData.datas)
                childListLiveData.value = currentList
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
            childListLiveData.value = mutableListOf()
            checkPositionLiveData.value = position
        }
        launch(
            block = {
                val treeList = treeListLiveData.value ?: return@launch
                val id = treeList[position].id
                val childrenData = mRespository.getChildrenData(INITIAL_PAGE, id)
                page = childrenData.curPage
                childListLiveData.value = childrenData.datas.toMutableList()

                refreshStatus.value = false
            },
            error = {
                refreshStatus.value = false
                reloadStatus.value = true
            }
        )
    }
}