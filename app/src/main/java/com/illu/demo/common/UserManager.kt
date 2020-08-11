package com.illu.demo.common

import com.google.gson.Gson
import com.illu.baselibrary.core.getSpValue
import com.illu.demo.bean.UserInfo

object UserManager {

    private const val user_data = "user_data"
    private val mGson by lazy { Gson() }

    fun getUserInfo(): UserInfo? {
        val userInfo =  getSpValue(user_data, "")
        return if (userInfo.isNotEmpty()) {
            mGson.fromJson(userInfo, UserInfo::class.java)
        } else {
            null
        }
    }
}