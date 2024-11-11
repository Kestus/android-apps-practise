package com.kes.app045_kt_currencies.data.network

import com.kes.app045_kt_currencies.data.network.model.CurrenciesResult
import com.kes.app045_kt_currencies.data.network.model.PriceListDto
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiService {

    @GET("/npm/@fawazahmed0/currency-api@latest/v1/currencies.json")
    suspend fun getLatest(): CurrenciesResult

    @GET("/npm/@fawazahmed0/currency-api@latest/v1/currencies/{code}.json")
    suspend fun getCurrency(@Path("code") code: String): PriceListDto

    /**
     * @param date - Date format: YYYY-MM-DD
     * */
    @GET("/npm/@fawazahmed0/currency-api@{date}/v1/currencies/{code}.json")
    suspend fun getCurrencyByDate(
        @Path("code") code: String,
        @Path("date") date: String
    ): PriceListDto

}

