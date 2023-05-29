package com.achsanit.newsnippet.utils

import com.achsanit.newsnippet.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class CustomInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().newBuilder()

        newRequest.addHeader("X-Api-Key", BuildConfig.NEWS_API_KEY)

        return chain.proceed(newRequest.build())
    }
}