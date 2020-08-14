package com.illu.demo.ui.home.square

import androidx.lifecycle.MutableLiveData
import com.illu.demo.base.BaseViewModel
import com.illu.demo.common.loadmore.LoadMoreStatus

class SquareViewModel : BaseViewModel() {

    companion object {
        const val INITIAL_PAGE = 0
    }

    val refreshStatus = MutableLiveData<Boolean>()
    val sqareDataList = MutableLiveData<MutableList<SquareBean>>()
    val loadMoreStatus = MutableLiveData<LoadMoreStatus>()

    private var page = INITIAL_PAGE

    fun getSquareData() {
        refreshStatus.value = true
        launch(
            block = {
                val squareData = mRespository.getSquareData(INITIAL_PAGE).apiData()
                page = squareData.curPage
                sqareDataList.value = squareData.datas.toMutableList()
                refreshStatus.value = false
            },
            error = {
                refreshStatus.value = false
            }
        )
    }

    fun loadMore() {
        loadMoreStatus.value = LoadMoreStatus.LOADING
        launch(
            block = {
                val squareData = mRespository.getSquareData(page).apiData()
                page = squareData.curPage

                val currentList = sqareDataList.value ?: mutableListOf()
                currentList.addAll(squareData.datas)
                sqareDataList.value = currentList
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
}