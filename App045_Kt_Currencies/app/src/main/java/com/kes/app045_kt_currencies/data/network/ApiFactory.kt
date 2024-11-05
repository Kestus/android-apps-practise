package com.kes.app045_kt_currencies.data.network

import com.google.gson.GsonBuilder
import com.kes.app045_kt_currencies.data.network.model.PriceListResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiFactory {
    private const val BASE_URL = "https://cdn.jsdelivr.net/"
    private val gson = GsonBuilder()
        .registerTypeAdapter(PriceListResponse::class.java, PriceListResponse())
        .create()

    private val instance by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    fun getService(): ApiService {
        return instance.create(ApiService::class.java)
    }

}