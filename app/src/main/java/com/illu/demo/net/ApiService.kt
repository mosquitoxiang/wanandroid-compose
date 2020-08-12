package com.illu.demo.net

import com.illu.demo.bean.PageBean
import com.illu.demo.ui.home.hot.HotBean
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("/article/top/json")
    suspend fun getTopArticleList(): ApiResult<List<HotBean>>

    @GET("/article/list/{page}/json")
    suspend fun getArticleList(@Path("page") page: Int): ApiResult<PageBean<HotBean>>
}