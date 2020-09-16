package com.illu.demo.ui.find

import androidx.lifecycle.MutableLiveData
import com.illu.demo.base.BaseViewModel
import kotlinx.coroutines.Job

class FindViewModel : BaseViewModel() {

    val refreshStatus = MutableLiveData<Boolean>()
    val reloadStatus = MutableLiveData<Boolean>()
    val bannerList = MutableLiveData<List<Banner>>()
    val hotKeyList = MutableLiveData<MutableList<HotKey>>()
    val commonWebList = MutableLiveData<List<CommonWeb>>()

    private var job: Job? = null

    fun refreshData() {
        refreshStatus.value = true
        reloadStatus.value = false
        job = launch(
            block = {
                val banner = mRespository.getBanner()
                bannerList.value = banner
                val hotKey = mRespository.getHotKey()
                hotKeyList.value = hotKey
                val web = mRespository.getCommonWeb()
                commonWebList.value = web
                refreshStatus.value = false
            },
            error = {
                refreshStatus.value = false
                reloadStatus.value =
                    bannerList.value.isNullOrEmpty() && hotKeyList.value.isNullOrEmpty() && commonWebList.value.isNullOrEmpty()
            }
        )
    }

}