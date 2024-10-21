package com.kes.app045_kt_currencies.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kes.app045_kt_currencies.data.mapper.CurrencyMapper
import com.kes.app045_kt_currencies.data.network.ApiFactory
import com.kes.app045_kt_currencies.domain.Repository
import com.kes.app045_kt_currencies.domain.model.CurrencyItem
import com.kes.app045_kt_currencies.domain.model.RelativePriceList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryImpl() : Repository {


    private val apiService = ApiFactory.getService()

    override fun getAll(): LiveData<List<CurrencyItem>> {
        val liveData: MutableLiveData<List<CurrencyItem>> = MutableLiveData()
        apiService.getLatest().enqueue(object : Callback<Map<String, String>> {
            override fun onResponse(
                p0: Call<Map<String, String>>,
                p1: Response<Map<String, String>>
            ) {
                val data = p1.body() ?: return onFailure(p0, Throwable("api call returned empty"))

                liveData.value = data
                    .filter {
                        it.key.length == 3
                    }
                    .map { CurrencyMapper.mapEntryToDBModel(it) }
                    .map { CurrencyMapper.mapDBModelToItem(it) }

            }

            override fun onFailure(p0: Call<Map<String, String>>, p1: Throwable) {
                Log.e("ERROR_API", p1.message.toString())
            }
        })

        return liveData
    }

    override fun getCurrencyByID(id: Int): CurrencyItem {
        TODO("Not yet implemented")
    }

    override fun getCurrencyByCode(code: String): CurrencyItem {
        TODO("Not yet implemented")
    }

    override fun getRelativePrice(code: String): RelativePriceList {
        TODO("Not yet implemented")
    }
}