package com.illu.demo.common.repository

import com.illu.demo.net.HttpUtils

class AppRepository {
    suspend fun getTopArticleList() = HttpUtils.service.getTopArticleList().apiData()

    suspend fun getArticleList(page: Int) = HttpUtils.service.getArticleList(page).apiData()

    suspend fun login(tietAccount: String, tietPassword: String) = HttpUtils.service.login(tietAccount, tietPassword).apiData()

    suspend fun register(tietAccount: String, tietPassword: String, tietConfirm: String) =
        HttpUtils.service.register(tietAccount, tietPassword, tietConfirm).apiData()

    suspend fun getSquareData(page: Int) = HttpUtils.service.getSquareData(page).apiData()

    suspend fun getTreeData() = HttpUtils.service.getTreeData().apiData()

    suspend fun getChildrenData(page: Int, id: Int) = HttpUtils.service.getChildrenData(page, id).apiData()

    suspend fun getGzhName() = HttpUtils.service.getGzhName().apiData()

    suspend fun getGzhArticle(id: Int, page: Int) = HttpUtils.service.getGzhArticle(id, page).apiData()

    suspend fun getCollectionList(page: Int) = HttpUtils.service.getCollectionList(page).apiData()

    suspend fun collect(id: Int) = HttpUtils.service.collect(id)

    suspend fun unCollect(id: Int) = HttpUtils.service.uncollect(id)
}