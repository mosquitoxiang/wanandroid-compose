package com.illu.demo.net

import com.illu.demo.bean.PageBean
import com.illu.demo.bean.UserInfo
import com.illu.demo.ui.home.hot.HotBean
import retrofit2.http.*

interface ApiService {

    @GET("/article/top/json")
    suspend fun getTopArticleList(): ApiResult<List<HotBean>>

    @GET("/article/list/{page}/json")
    suspend fun getArticleList(@Path("page") page: Int): ApiResult<PageBean<HotBean>>

    @FormUrlEncoded
    @POST("/user/login")
    suspend fun requestLogin(
        @Field("username") userName: String,
        @Field("password") password: String
    ): ApiResult<UserInfo>
}