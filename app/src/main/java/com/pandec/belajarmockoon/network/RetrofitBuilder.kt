package com.pandec.belajarmockoon.network


import android.annotation.SuppressLint
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.pandec.belajarmockoon.MockoonApplication.Companion.context
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitBuilder {
    lateinit var request: Request
    private val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

//    val headerInterceptor = object : Interceptor {
//        @SuppressLint("ConstantLocale")
//        override fun intercept(chain: Interceptor.Chain): Response {
//            request = chain.request()
//
//            request = request.newBuilder().addHeader("h", "sk").build()
//
//            val response = chain.proceed(request)
//
//            return response
//        }
//    }

    private val okhttp = OkHttpClient.Builder()
        .callTimeout(60, TimeUnit.SECONDS)
//        .addInterceptor(headerInterceptor)
        .addInterceptor(
            ChuckerInterceptor.Builder(context!!)
                .collector(ChuckerCollector(context!!))
                .maxContentLength(250000L)
                .redactHeaders(emptySet())
                .alwaysReadResponseBody(false)
                .build()
        )
        .addInterceptor(logger)

    private val builder = Retrofit.Builder().baseUrl(Static.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okhttp.build())

    private val retrofit = builder.build()

    fun <T> buildService(serviceType: Class<T>): T{
        return retrofit.create(serviceType)
    }

}