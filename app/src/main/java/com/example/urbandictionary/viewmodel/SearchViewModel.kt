package com.example.urbandictionary.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.urbandictionary.data.model.ResponseUrban
import com.example.urbandictionary.data.network.ApiService
import com.example.urbandictionary.data.network.NetworkComponent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class SearchViewModel : ViewModel() {

    init {
        NetworkComponent.instance?.inject(this)
    }

    data class Resource<T, U>(val data: T?, val errorMessage: U?)
    val termLiveData by lazy { MutableLiveData<Resource<ResponseUrban, String>>() }
    @Inject lateinit var apiService: ApiService

    fun getSearchData(term: String) =
        apiService.getDefinitions(term)
            .enqueue(object : Callback<ResponseUrban> {
                override fun onFailure(call: Call<ResponseUrban>, t: Throwable) {
                    termLiveData.postValue(Resource(data = null, errorMessage = t.message))
                }

                override fun onResponse(call: Call<ResponseUrban>, response: Response<ResponseUrban>) {
                    if ((response.body() as ResponseUrban).list.isEmpty()) {
                        termLiveData.postValue(Resource(data = null, errorMessage = "No definitions found for word."))
                    } else {
                        termLiveData.postValue(Resource(data = response.body(), errorMessage = null))
                    }
                }
            })
}