package com.manabboro.assignment.data.remote

import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val modifiedRequest = originalRequest.newBuilder()
            .addHeader("x-api-key", "reqres-free-v1")
            .build()
        return chain.proceed(modifiedRequest)
    }
}
