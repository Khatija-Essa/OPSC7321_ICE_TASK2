package com.example.currencyconverter

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

data class CurrencyResponse(
    val base_currency: String,
    val amount: String,
    val rates: Map<String, String>
)

interface CurrencyApi {
    @GET("v2/currency/convert")
    suspend fun convertCurrency(
        @Query("api_key") apiKey: String,
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("amount") amount: String
    ): CurrencyResponse
}

object RetrofitInstance {
    private const val BASE_URL = "https://api.getgeoapi.com/api/"

    val api: CurrencyApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CurrencyApi::class.java)
    }
}