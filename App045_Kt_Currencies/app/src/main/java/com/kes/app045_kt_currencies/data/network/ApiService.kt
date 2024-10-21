package com.kes.app045_kt_currencies.data.network

import com.kes.app045_kt_currencies.data.network.model.CurrenciesResult
import com.kes.app045_kt_currencies.data.network.model.PriceListResult
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiService {

    @GET("/npm/@fawazahmed0/currency-api@latest/v1/currencies.json")
    fun getLatest(): Call<Map<String, String>>

    @GET("/npm/@fawazahmed0/currency-api@latest/v1/currencies/{code}.json")
    fun getCurrency(@Path("code") code: String): Call<PriceListResult>

    /**
     * @param date - Date format: YYYY-MM-DD
     * */
    @GET("/npm/@fawazahmed0/currency-api@{date}/v1/currencies/{code}.json")
    fun getCurrencyByDate(
        @Path("code") code: String,
        @Path("date") date: String
    ): Call<PriceListResult>

}

