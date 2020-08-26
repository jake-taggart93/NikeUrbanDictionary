package com.example.urbandictionary

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.urbandictionary.data.model.ResponseUrban
import com.example.urbandictionary.viewmodel.SearchViewModel
import com.nhaarman.mockitokotlin2.any
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.anyString
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SearchViewModelTest {
    @get:Rule
    val instantTaskExecutor = InstantTaskExecutorRule()
    @Mock
    private lateinit var searchViewModel: SearchViewModel
    @Mock
    private lateinit var resultData: MutableLiveData<SearchViewModel.Resource<ResponseUrban, String>>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        `when`(searchViewModel.termLiveData).thenReturn(resultData)
        `when`(searchViewModel.getSearchData("hey")).then{
            searchViewModel.termLiveData.postValue(SearchViewModel.Resource(any(), null))
        }
        `when`(searchViewModel.getSearchData("INVALIDxxTERM")).then {
            searchViewModel.termLiveData.postValue(SearchViewModel.Resource(null, anyString()))
        }
    }

    @Test
    fun testPassResponse() {
        searchViewModel.getSearchData("hey")
        searchViewModel.termLiveData.observeForever{
            Assert.assertNotNull(it.data)
            Assert.assertNull(it.errorMessage)
        }
    }

    @Test
    fun testFailResponse() {
        searchViewModel.getSearchData("INVALIDxxTERM")
        searchViewModel.termLiveData.observeForever{
            Assert.assertNotNull(it.errorMessage)
            Assert.assertNull(it.data)
        }
    }
}