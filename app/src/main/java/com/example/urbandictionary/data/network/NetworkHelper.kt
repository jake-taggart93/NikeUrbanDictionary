package com.example.urbandictionary.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://mashape-community-urban-dictionary.p.rapidapi.com/"

class NetworkHelper {
    private var instance: ApiService? = null
    fun getDictionaryApiService(): ApiService {
        if (instance == null) {
            instance = Retrofit.Builder().baseUrl(BASE_URL)
                .client(makeClient())
                .addConverterFactory(GsonConverterFactory.create()).build()
                .create(ApiService::class.java)
        }
        return instance!!
    }

    private fun makeClient() =
        OkHttpClient.Builder().addInterceptor(makeInterceptor()).build()

    private fun makeInterceptor() =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
}