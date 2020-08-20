package com.example.urbandictionary.data.network

import com.example.urbandictionary.data.model.ResponseUrban
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

private const val HEADER_HOST = "x-rapidapi-host"
const val HOST = "mashape-community-urban-dictionary.p.rapidapi.com"
private const val HEADER__KEY = "x-rapidapi-key"
const val KEY = "876dfc44f9mshf1dd94e1d58ff03p1e147djsn8f3132ca485a"
private const val PATH = "define"
private const val TERM = "term"

interface ApiService {
    @Headers("$HEADER_HOST: $HOST", "$HEADER__KEY: $KEY")
    @GET(PATH)
    fun getDefinitions(@Query(TERM) word: String): Call<ResponseUrban>
}