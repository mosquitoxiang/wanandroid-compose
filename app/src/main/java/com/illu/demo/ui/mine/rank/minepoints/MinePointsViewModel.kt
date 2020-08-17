package com.illu.demo.ui.mine.rank.minepoints

import androidx.lifecycle.MutableLiveData
import com.chad.library.adapter.base.loadmore.LoadMoreView
import com.illu.demo.base.BaseViewModel
import com.illu.demo.common.loadmore.CommonLoadMoreView
import com.illu.demo.common.loadmore.LoadMoreStatus

class MinePointsViewModel : BaseViewModel() {

    companion object {
        const val INITIAL_PAGE = 1
    }

    private var page = INITIAL_PAGE

    val refreshStatus = MutableLiveData<Boolean>()
    val loadMoreStatus = MutableLiveData<LoadMoreStatus>()
    val reloadStatus = MutableLiveData<Boolean>()
    val minePoints = MutableLiveData<MinePointsBean>()
    val minePointsList = MutableLiveData<MutableList<MinePointsListBean>>()

    fun refreshData() {
        refreshStatus.value = true
        reloadStatus.value = false
        launch(
            block = {
                val pointsDefferd = async { mRespository.getMinePoints() }
                val pointsListDefferd = async { mRespository.getMinePointsList(INITIAL_PAGE) }
                val points = pointsDefferd.await()
                val pointsList = pointsListDefferd.await()
                minePoints.value = points
                minePointsList.value = pointsList.datas.toMutableList()
                refreshStatus.value = false
            },
            error = {
                refreshStatus.value = false
                reloadStatus.value = true
            }
        )
    }

    fun loadMore() {
        loadMoreStatus.value = LoadMoreStatus.LOADING
        launch(
            block = {
                val pointsList = mRespository.getMinePointsList(page + 1)
                val currentList = minePointsList.value ?: mutableListOf()
                currentList.addAll(pointsList.datas)
                minePointsList.value = currentList
                loadMoreStatus.value = if (pointsList.offset >= pointsList.total) {
                    LoadMoreStatus.END
                } else {
                    LoadMoreStatus.COMPLETED
                }
                page = pointsList.curPage
            },
            error = {
                loadMoreStatus.value = LoadMoreStatus.ERROR
            }
        )
    }
}