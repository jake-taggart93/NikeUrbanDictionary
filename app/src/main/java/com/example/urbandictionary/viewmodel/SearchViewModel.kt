package com.example.urbandictionary.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.urbandictionary.UrbanDictionary
import com.example.urbandictionary.data.model.ResponseUrban
import com.example.urbandictionary.data.network.ApiService
import com.example.urbandictionary.data.network.NetworkComponent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class SearchViewModel : ViewModel() {

    init {
        NetworkComponent.instance?.inject(this)
    }

    data class Resource<T, U>(val data: T?, val errorMessage: U?)
    val termLiveData by lazy { MutableLiveData<Resource<ResponseUrban, String>>() }
    @Inject lateinit var retrofit: Retrofit

    fun getSearchData(term: String) =
        retrofit.create(ApiService::class.java).getDefinitions(term)
            .enqueue(object : Callback<ResponseUrban> {
                override fun onFailure(call: Call<ResponseUrban>, t: Throwable) {
                    termLiveData.value = Resource(data = null, errorMessage = t.message)
                }

                override fun onResponse(call: Call<ResponseUrban>, response: Response<ResponseUrban>) {
                    termLiveData.value = Resource(data = response.body(), errorMessage = null)
                }
            })
}