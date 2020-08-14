package com.illu.demo.ui.login

import androidx.lifecycle.MutableLiveData
import com.illu.demo.base.BaseViewModel
import com.illu.demo.common.UserManager

class LoginViewModel : BaseViewModel() {

    val loginStatus = MutableLiveData<Boolean>()
    val loginResult = MutableLiveData<Boolean>()

    fun requestLogin(tietAccount: String, tietPassword: String) {
        loginStatus.value = true
        launch(
            block = {
                val userInfo = mRespository.requestLogin(tietAccount, tietPassword)
                UserManager.setUserInfo(userInfo)
                loginStatus.value = false
                loginResult.value = true
            },
            error = {
                loginStatus.value = false
                loginResult.value = false
            }
        )
    }
}