package com.example.urbandictionary

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.example.urbandictionary.data.model.ResponseUrban
import com.example.urbandictionary.data.network.ApiService
import com.example.urbandictionary.data.network.NetworkHelper
import com.example.urbandictionary.viewmodel.SearchViewModel
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import retrofit2.Response

class SearchViewModelTest {
/*    @Mock private lateinit var apiService: ApiService
    @Mock private lateinit var networkHelper: NetworkHelper
    private lateinit var searchViewModel: SearchViewModel
    @Mock private lateinit var resultLiveData: MutableLiveData<SearchViewModel.Resource<ResponseUrban, String>>
    @Mock private lateinit var application: Application

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        `when`(networkHelper.getDictionaryApiService().getDefinitions("yo")).thenReturn(null)
    }

    private fun <T, U> assertSuccessResource(resource: SearchViewModel.Resource<ResponseUrban, String>?) {
        assertNotNull(resource?.data)
        assertNull(resource?.errorMessage)
    }

    private fun <T, U> assertErrorResource(resource: SearchViewModel.Resource<ResponseUrban, String>?) {
        assertNull(resource?.data)
        assertNotNull(resource?.errorMessage)
    }*/
}