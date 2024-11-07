package com.kes.app045_kt_currencies.data.network.model

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

data class PriceListResponse(
    var baseCurrencyCode: String? = null,
    var date: String? = null,
    var priceMap: Map<String, Double>? = null
) : JsonDeserializer<PriceListResponse> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): PriceListResponse {
        val result = PriceListResponse()

        json?.let {
            val obj = json.asJsonObject
            require(obj.keySet().size == FIELDS_EXPECTED)

            for ((key, value) in obj.asMap()) {
                when {
                    value.isJsonPrimitive -> result.date = value.asString
                    value.isJsonObject -> {
                        result.baseCurrencyCode = key
                        result.priceMap = value.asJsonObject
                            .asMap()
                            .mapValues { it.value.asDouble }
                    }

                    else -> throw Throwable("unexpected field value type")
                }
            }
        }

        return result
    }

    fun filterPriceMap(currencyCodes: List<String>) {
        priceMap?.let { map ->
            priceMap =
                map.filterKeys { currencyCodes.contains(it) }    // filter-out currencies not already in db
                   .filterKeys { it != baseCurrencyCode }        // filter-out self price
        }
    }

    companion object {
        private const val FIELDS_EXPECTED = 2
    }
}


