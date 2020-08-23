package com.illu.demo.common.repository

import com.illu.demo.common.room.History
import com.illu.demo.common.room.RoomHelper
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

    suspend fun collect(id: Int) = HttpUtils.service.collect(id).apiData()

    suspend fun unCollect(id: Int) = HttpUtils.service.uncollect(id).apiData()

    suspend fun getMinePointsList(page: Int) = HttpUtils.service.getMinePointsList(page).apiData()

    suspend fun getMinePoints() = HttpUtils.service.getMinePoints().apiData()

    suspend fun getPointsRank(page: Int) = HttpUtils.service.getPointsRank(page).apiData()

    suspend fun getCategory() = HttpUtils.service.getSystem().apiData()

    suspend fun getSystemArticle(page: Int, id: Int) = HttpUtils.service.getSystemArticle(page, id).apiData()

    suspend fun getBanner() = HttpUtils.service.getBanner().apiData()

    suspend fun getCommonWeb() = HttpUtils.service.getCommonWeb().apiData()

    suspend fun getHotKey() = HttpUtils.service.getHotKey().apiData()

    suspend fun getNav() = HttpUtils.service.getNav().apiData()

    suspend fun search(page: Int, key: String) = HttpUtils.service.search(page, key).apiData()

    suspend fun getHistory() = RoomHelper.queryHistory()

    suspend fun addHistory(history: History) = RoomHelper.addHistory(history)

    suspend fun deleteHistory(content: String) = RoomHelper.delete(content)

    suspend fun share(title: String, link: String) = HttpUtils.service.share(title, link).apiData()
}