package com.example.urbandictionary.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.urbandictionary.R
import com.example.urbandictionary.data.model.ResponseUrban
import com.example.urbandictionary.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.activity_search_landing.*

private const val ERROR = "Error"

class SearchLandingPage : AppCompatActivity() {

    private val urbanViewModel by lazy { SearchViewModel() }
    private lateinit var currentTerm: String
    private lateinit var currentAdapter: ResultAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_landing)
    }

    private val definitionObserver by lazy {
        Observer<SearchViewModel.Resource<ResponseUrban, String>> { response ->
            visibilitySwitch(true)
            Log.d("TAG", response.toString())
            response.data?.definitions?.let {
                currentAdapter = ResultAdapter(it)
                rvDefinitions.adapter = currentAdapter
                rvDefinitions.visibility = View.VISIBLE
                val layoutManager = LinearLayoutManager(this)
                rvDefinitions.layoutManager = layoutManager
            }
            response.errorMessage?.let {
                AlertDialog.Builder(this).apply {
                    setTitle(ERROR)
                    setMessage(it)
                    setPositiveButton(android.R.string.ok, null)
                }.show()
            }
        }
    }

    fun onClick(view: View) =
        when (view.id) {
            btnSearch.id -> {
                if (tieEntry.text.isNullOrEmpty().not()) {
                    visibilitySwitch(false)
                    with(tieEntry.text.toString()) {
                        urbanViewModel.getSearchData(this)
                        currentTerm = this
                    }
                    urbanViewModel.getSearchData(tieEntry.text.toString())
                    urbanViewModel.termLiveData.observe(this, definitionObserver)
                } else {
                    Toast.makeText(
                        this,
                        "If you would like to search, please type a word.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            btnClear.id -> //wipe out existing search results for end user
                rvDefinitions.adapter = null
            tvSortUp.id -> sortAdapter(true)
            else -> sortAdapter(false)
        }

    private fun sortAdapter(up: Boolean) {

    }

    private fun visibilitySwitch(majority: Boolean) =
        if (majority) {
            progressBar.visibility = View.GONE
            tieEntry.isEnabled = true
            tilEntry.isEnabled = true
            btnClear.isEnabled = true
            btnSearch.isEnabled = true
        } else {
            progressBar.visibility = View.VISIBLE
            tieEntry.isEnabled = false
            tilEntry.isEnabled = false
            btnClear.isEnabled = false
            btnSearch.isEnabled = false
        }
}
