package com.illu.demo.net

import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.illu.baselibrary.App
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object HttpUtils {

    private const val BASE_URL = "https://www.wanandroid.com"

    private val cookiePersistor = SharedPrefsCookiePersistor(App.INSTANCE)
    private val cookieJar = PersistentCookieJar(SetCookieCache(), cookiePersistor)

    private val okHttpClient = OkHttpClient.Builder()
        .callTimeout(10, TimeUnit.SECONDS)
        .addInterceptor(LogInterceptor())
        .cookieJar(cookieJar)
        .build()

    private val retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(ApiService::class.java)

    fun clearCookie() = cookieJar.clear()

    fun hasCookie() = cookiePersistor.loadAll().isNotEmpty()
}