package com.julian.taipeitour.common.http

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthenticationInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()
        val builder: Request.Builder = original.newBuilder()
            .addHeader("Content-Type", "application/json")
            .addHeader("accept", "application/json")

        //若有需要做驗證可新增於此(e.g. JWT)
        return chain.proceed(builder.build())
    }
}