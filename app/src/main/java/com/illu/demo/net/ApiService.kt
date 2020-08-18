package com.illu.demo.net

import com.illu.demo.bean.ArticleBean
import com.illu.demo.bean.PageBean
import com.illu.demo.bean.UserInfo
import com.illu.demo.ui.home.project.CategoryBean
import com.illu.demo.ui.mine.rank.minepoints.MinePointsBean
import com.illu.demo.ui.mine.rank.minepoints.MinePointsListBean
import com.illu.demo.ui.mine.rank.pointsrank.Rank
import retrofit2.http.*

interface ApiService {

    @GET("/article/top/json")
    suspend fun getTopArticleList(): ApiResult<List<ArticleBean>>

    @GET("/article/list/{page}/json")
    suspend fun getArticleList(@Path("page") page: Int): ApiResult<PageBean<ArticleBean>>

    @FormUrlEncoded
    @POST("/user/login")
    suspend fun login(
        @Field("username") userName: String,
        @Field("password") password: String
    ): ApiResult<UserInfo>

    @FormUrlEncoded
    @POST("/user/register")
    suspend fun register(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("repassword") repassword: String
    ): ApiResult<UserInfo>

    @GET("/user_article/list/{page}/json")
    suspend fun getSquareData(@Path("page") page: Int): ApiResult<PageBean<ArticleBean>>

    @GET("/project/tree/json")
    suspend fun getTreeData(): ApiResult<List<CategoryBean>>

    @GET("/project/list/{page}/json")
    suspend fun getChildrenData(
        @Path("page") page: Int,
        @Query("cid") cid: Int
    ): ApiResult<PageBean<ArticleBean>>

    @GET("/wxarticle/chapters/json")
    suspend fun getGzhName(): ApiResult<List<CategoryBean>>

    @GET("wxarticle/list/{id}/{page}/json")
    suspend fun getGzhArticle(
        @Path("id") id: Int,
        @Path("page") page: Int
    ): ApiResult<PageBean<ArticleBean>>

    @GET("lg/collect/list/{page}/json")
    suspend fun getCollectionList(@Path("page") page: Int): ApiResult<PageBean<ArticleBean>>

    @POST("lg/collect/{id}/json")
    suspend fun collect(@Path("id") id: Int): ApiResult<Any?>

    @POST("lg/uncollect_originId/{id}/json")
    suspend fun uncollect(@Path("id") id: Int): ApiResult<Any?>

    @GET("/lg/coin/list/{page}/json")
    suspend fun getMinePointsList(@Path("page") page: Int): ApiResult<PageBean<MinePointsListBean>>

    @GET("/lg/coin/userinfo/json")
    suspend fun getMinePoints(): ApiResult<MinePointsBean>

    @GET("/coin/rank/{page}/json")
    suspend fun getPointsRank(@Path("page") page: Int): ApiResult<PageBean<Rank>>

    @GET("/tree/json")
    suspend fun getSystem(): ApiResult<MutableList<CategoryBean>>

    @GET("/article/list/{page}/json")
    suspend fun getSystemArticle(
        @Path("page") page: Int,
        @Query("cid") id: Int
    ): ApiResult<PageBean<ArticleBean>>
}