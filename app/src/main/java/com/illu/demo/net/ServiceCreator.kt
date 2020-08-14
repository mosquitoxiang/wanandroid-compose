package main.java.com.zhangyu.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.webkit.WebSettings
import com.illu.baselibrary.App
import com.illu.demo.net.LogInterceptor

object ServiceCreator {

    private const val BASE_URL = ""
    private lateinit var okhttpClient: OkHttpClient

    private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(getOkHttpClient())
            .build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    inline fun <reified T> create(): Int = create(T::class.java)

    private fun getOkHttpClient(): OkHttpClient {
        okhttpClient = OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .addInterceptor(LogInterceptor())
                .addInterceptor(Interceptor { chain ->
                    val original = chain.request()
                    val requestBuilder = original.newBuilder()
                            .removeHeader("User-Agent")
                            .header("User-Agent", getUserAgent())
                    val request = requestBuilder.build()
                    chain.proceed(request)
                })
                .build()
        return okhttpClient
    }

    fun getUserAgent(): String {
        var userAgent = ""
        try {
            userAgent = WebSettings.getDefaultUserAgent(App.INSTANCE)
        } catch (e: Exception) {
            userAgent = System.getProperty("http.agent")!!
        }
        userAgent = System.getProperty("http.agent")!!
        //调整编码，防止中文出错
        val sb = StringBuffer()
        var i = 0
        val length = userAgent.length
        while (i < length) {
            val c = userAgent[i]
            if (c <= '\u001f' || c >= '\u007f') {
                sb.append(String.format("\\u%04x", c.toInt()))
            } else {
                sb.append(c)
            }
            i++
        }
        return sb.toString()
    }
}