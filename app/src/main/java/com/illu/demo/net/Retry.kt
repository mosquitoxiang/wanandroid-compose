package com.illu.demo.net

import okhttp3.Interceptor
import okhttp3.Response

class Retry(val maxRetry: Int) : Interceptor{

    private var retryNum = 0

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var response = chain.proceed(request)
        while (!response.isSuccessful && retryNum < maxRetry){
            retryNum ++
            response = chain.proceed(request)
        }
        return response
    }
}