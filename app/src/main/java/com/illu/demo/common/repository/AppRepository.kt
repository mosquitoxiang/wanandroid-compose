package com.illu.demo.common.repository

import com.illu.demo.net.HttpUtils

class AppRepository {
    suspend fun getTopArticleList() = HttpUtils.service.getTopArticleList().apiData()
    suspend fun getArticleList(page: Int) = HttpUtils.service.getArticleList(page).apiData()
    suspend fun requestLogin(tietAccount: String, tietPassword: String) = HttpUtils.service.requestLogin(tietAccount, tietPassword).apiData()
}