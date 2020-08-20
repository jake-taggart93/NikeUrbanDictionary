package com.example.urbandictionary.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.urbandictionary.data.model.ResponseUrban
import com.example.urbandictionary.data.network.NetworkHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel : ViewModel() {

    data class Resource<T, U>(val data: T?, val errorMessage: U?)
    val termLiveData by lazy { MutableLiveData<Resource<ResponseUrban, String>>() }

    fun getSearchData(term: String) =
        NetworkHelper().getDictionaryApiService().getDefinitions(term).enqueue(object : Callback<ResponseUrban> {
            override fun onFailure(call: Call<ResponseUrban>, t: Throwable) {
                termLiveData.postValue(Resource(data = null, errorMessage = t.message))
            }

            override fun onResponse(call: Call<ResponseUrban>, response: Response<ResponseUrban>) {
                Log.d("TAG", response.raw().toString())
                Log.d("TAG", response.toString())
                Log.d("TAG", response.errorBody().toString())
                termLiveData.postValue(Resource(data = response.body(), errorMessage = null))
            }
        })
}