package com.illu.demo.common

import com.google.gson.Gson
import com.illu.baselibrary.core.getSpValue
import com.illu.baselibrary.core.putSpValue
import com.illu.demo.bean.UserInfo

object UserManager {

    private const val USER_DATA = "user_data"
    private val mGson by lazy { Gson() }

    fun getUserInfo(): UserInfo? {
        val userInfo =  getSpValue(USER_DATA, "")
        return if (userInfo.isNotEmpty()) {
            mGson.fromJson(userInfo, UserInfo::class.java)
        } else {
            null
        }
    }

    fun setUserInfo(userInfo: UserInfo) {
        putSpValue(USER_DATA, mGson.toJson(userInfo))
    }
}