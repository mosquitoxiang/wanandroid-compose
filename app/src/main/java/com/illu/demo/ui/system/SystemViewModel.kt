package com.illu.demo.ui.system

import androidx.lifecycle.MutableLiveData
import com.illu.demo.base.BaseViewModel
import com.illu.demo.ui.home.project.CategoryBean

class SystemViewModel : BaseViewModel() {

    val categoryList = MutableLiveData<MutableList<CategoryBean>>()
    val loadingStatus = MutableLiveData<Boolean>()
    val reloadStatus = MutableLiveData<Boolean>()

    fun getCategory() {
        loadingStatus.value = true
        reloadStatus.value = false
        launch(
            block = {
                val category = mRespository.getCategory()
                categoryList.value = category
                loadingStatus.value = false
            },
            error = {
                loadingStatus.value = false
                reloadStatus.value = true
            }
        )
    }
}