package com.illu.demo.ui.register

import androidx.lifecycle.MutableLiveData
import com.illu.demo.base.BaseViewModel
import com.illu.demo.common.UserManager

class RegisterViewModel : BaseViewModel() {

    val registerStatus = MutableLiveData<Boolean>()
    val registerResult = MutableLiveData<Boolean>()

    fun register(tietAccount: String, tietPassword: String, tietConfirm: String) {
        registerStatus.value = true
        launch(
            block = {
                val userInfo = mRespository.register(tietAccount, tietPassword, tietConfirm)
                UserManager.setUserInfo(userInfo)
                registerStatus.value = false
                registerResult.value = true
            },
            error = {
                registerStatus.value = false
                registerResult.value = false
            }
        )
    }
}