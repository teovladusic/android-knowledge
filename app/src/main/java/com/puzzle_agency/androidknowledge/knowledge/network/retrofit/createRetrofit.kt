package com.puzzle_agency.androidknowledge.knowledge.network.retrofit

import com.google.gson.GsonBuilder
import com.puzzle_agency.androidknowledge.knowledge.network.retrofit.api_result.ApiResultAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Suppress("Unused")
fun createRetrofit(baseUrl: String, okHttpClient: OkHttpClient): Retrofit =
    Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().serializeNulls().create()
            )
        )
        .addCallAdapterFactory(ApiResultAdapterFactory())
        .build()
