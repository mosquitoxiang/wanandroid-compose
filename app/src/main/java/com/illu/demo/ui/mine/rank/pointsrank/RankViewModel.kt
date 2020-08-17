package com.illu.demo.ui.mine.rank.pointsrank

import androidx.lifecycle.MutableLiveData
import com.illu.demo.base.BaseViewModel
import com.illu.demo.common.loadmore.LoadMoreStatus

class RankViewModel : BaseViewModel() {

    companion object {
        const val INITIAL_PAGE = 1
    }

    private var page = INITIAL_PAGE

    val loadMoreStatus = MutableLiveData<LoadMoreStatus>()
    val refreshStatus = MutableLiveData<Boolean>()
    val reloadStatus = MutableLiveData<Boolean>()
    val rankList = MutableLiveData<MutableList<Rank>>()

    fun requestData() {
        refreshStatus.value = true
        reloadStatus.value = false
        launch(
            block = {
                val pagination = mRespository.getPointsRank(INITIAL_PAGE)
                val rank = pagination.datas.toMutableList()
                rankList.value = rank
                page = pagination.curPage
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
                val pagition = mRespository.getPointsRank(page + 1)
                val currentList = rankList.value ?: mutableListOf()
                currentList.addAll(pagition.datas)
                rankList.value = currentList
                loadMoreStatus.value = if (pagition.offset >= pagition.total) {
                    LoadMoreStatus.END
                } else {
                    LoadMoreStatus.COMPLETED
                }
                page = pagition.curPage
            },
            error = {
                loadMoreStatus.value = LoadMoreStatus.ERROR
            }
        )
    }

}