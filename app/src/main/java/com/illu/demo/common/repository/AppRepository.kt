package com.illu.demo.common.repository

import com.illu.demo.net.HttpUtils

class AppRepository {
    suspend fun getTopArticleList() = HttpUtils.service.getTopArticleList().apiData()

    suspend fun getArticleList(page: Int) = HttpUtils.service.getArticleList(page).apiData()

    suspend fun login(tietAccount: String, tietPassword: String) = HttpUtils.service.login(tietAccount, tietPassword).apiData()

    suspend fun register(tietAccount: String, tietPassword: String, tietConfirm: String) =
        HttpUtils.service.register(tietAccount, tietPassword, tietConfirm).apiData()

    suspend fun getSquareData(page: Int) = HttpUtils.service.getSquareData(page)
}