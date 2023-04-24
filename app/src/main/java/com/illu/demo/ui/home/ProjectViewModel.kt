package com.illu.demo.ui.home

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.MutableLiveData
import com.illu.demo.base.BaseViewModel
import com.illu.demo.bean.ArticleBean
import com.illu.demo.common.UserManager
import com.illu.demo.common.bus.Bus
import com.illu.demo.common.bus.USER_COLLECT_UPDATE
import com.illu.demo.common.isLogin
import com.illu.demo.ui.home.project.CategoryBean
import kotlinx.coroutines.flow.MutableStateFlow

class ProjectViewModel : BaseViewModel() {

    companion object {
        const val INITIAL_PAGE = 1
        const val INITIAL_CHECKED = 0
    }

    val refreshStatus = MutableStateFlow<Boolean?>(null)
    val treeListLiveData = MutableStateFlow<MutableList<CategoryBean>?>(null)
    val articleList = MutableStateFlow<MutableList<ArticleBean>?>(null)
    val checkPositionLiveData = MutableStateFlow<Int?>(null)

    private var page = INITIAL_PAGE

    init {
        getTreeData()
    }

    private fun getTreeData() {
        refreshStatus.value = true
        launch(
            block = {
                val responseList = mRespository.getTreeData()
                treeListLiveData.value = responseList.toMutableStateList()
                val id = responseList[INITIAL_CHECKED].id
                checkPositionLiveData.value = INITIAL_CHECKED
                val childrenData = mRespository.getChildrenData(INITIAL_PAGE, id)
                page = childrenData.curPage
                articleList.value = childrenData.datas.toMutableStateList()
                refreshStatus.value = false
            },
            error = {
                refreshStatus.value = false
            }
        )
    }

    fun loadMoreData() {
        launch(
            block = {
                val treeList = treeListLiveData.value ?:return@launch
                val checkPosition = checkPositionLiveData.value ?: return@launch
                val id = treeList[checkPosition].id
                val newChildData = mRespository.getChildrenData(page + 1, id)
                page = newChildData.curPage
                val currentList = articleList.value ?: mutableStateListOf()
                currentList.addAll(newChildData.datas)
                articleList.value = currentList
            },
            error = {

            }
        )
    }

    fun changePosition(position: Int = checkPositionLiveData.value ?: INITIAL_CHECKED) {
        refreshStatus.value = true
        if (position != checkPositionLiveData.value) {
            articleList.value = mutableStateListOf()
            checkPositionLiveData.value = position
        }
        launch(
            block = {
                val treeList = treeListLiveData.value ?: return@launch
                val id = treeList[position].id
                val childrenData = mRespository.getChildrenData(INITIAL_PAGE, id)
                page = childrenData.curPage
                articleList.value = childrenData.datas.toMutableStateList()

                refreshStatus.value = false
            },
            error = {
                refreshStatus.value = false
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