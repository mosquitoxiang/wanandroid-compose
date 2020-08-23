package com.illu.demo.ui.find.share

import androidx.lifecycle.MutableLiveData
import com.illu.demo.base.BaseViewModel
import com.illu.demo.common.UserManager

class ShareViewModel : BaseViewModel() {

    val shareResult = MutableLiveData<Boolean>()
    val submitStatus = MutableLiveData<Boolean>()
    val userName = MutableLiveData<String>()

    fun share(title: String, link: String) {
        submitStatus.value = true
        launch(
            block = {
                mRespository.share(title, link)
                shareResult.value = true
                submitStatus.value = false
            },
            error = {
                shareResult.value = false
                submitStatus.value = false
            }
        )
    }

    fun getUserInfo() {
        val userInfo = UserManager.getUserInfo()
        userName.value = if (userInfo?.username?.isEmpty()!!) {
            userInfo.nickname
        } else {
            userInfo.username
        }
    }
}