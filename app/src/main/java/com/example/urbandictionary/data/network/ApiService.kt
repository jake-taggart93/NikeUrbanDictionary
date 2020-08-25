package com.example.urbandictionary.data.network

import com.example.urbandictionary.data.model.ResponseUrban
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

const val HEADER_HOST = "x-rapidapi-host: mashape-community-urban-dictionary.p.rapidapi.com"
const val HEADER__KEY = "x-rapidapi-key: 876dfc44f9mshf1dd94e1d58ff03p1e147djsn8f3132ca485a"
const val PATH = "define"
const val TERM = "term"

interface ApiService {
    @Headers(HEADER_HOST, HEADER__KEY)
    @GET(PATH)
    fun getDefinitions(@Query(TERM) word: String): Call<ResponseUrban>
}