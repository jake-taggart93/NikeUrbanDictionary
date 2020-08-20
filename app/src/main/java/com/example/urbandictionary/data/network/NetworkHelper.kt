package com.example.urbandictionary.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://mashape-community-urban-dictionary.p.rapidapi.com"

class NetworkHelper {
    private var instance: ApiService? = null
    fun getDictionaryApiService(): ApiService {
        if (instance == null) {
            instance = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
                .create(ApiService::class.java)
        }
        return instance!!
    }
}