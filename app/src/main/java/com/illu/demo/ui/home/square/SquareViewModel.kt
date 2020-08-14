package com.illu.demo.ui.home.square

import androidx.lifecycle.MutableLiveData
import com.illu.demo.base.BaseViewModel

class SquareViewModel : BaseViewModel() {

    companion object {
        const val INITIAL_PAGE = 0
    }

    val refrestStatus = MutableLiveData<Boolean>()
    val sqareDataList = MutableLiveData<MutableList<SquareBean>>()

    private var page = INITIAL_PAGE

    fun getSquareData() {
        refrestStatus.value = true
        launch(
            block = {
                val squareData = mRespository.getSquareData(INITIAL_PAGE).apiData()
                page = squareData.curPage
                sqareDataList.value = squareData.datas.toMutableList()
                refrestStatus.value = false
            },
            error = {
                refrestStatus.value = false
            }
        )
    }
}