package com.illu.demo.ui.navigation

import androidx.lifecycle.MutableLiveData
import com.illu.demo.base.BaseViewModel

class NavigationViewModel : BaseViewModel() {

    val refreshStatus = MutableLiveData<Boolean>()
    val reloadStatus = MutableLiveData<Boolean>()
    val navList = MutableLiveData<MutableList<Nav>>()

    fun refreshData() {
        refreshStatus.value = true
        reloadStatus.value = false
        launch(
            block = {
                val nav = mRespository.getNav()
                navList.value = nav
                refreshStatus.value = false
            },
            error = {
                refreshStatus.value = false
                reloadStatus.value = navList.value.isNullOrEmpty()
            }
        )
    }
}