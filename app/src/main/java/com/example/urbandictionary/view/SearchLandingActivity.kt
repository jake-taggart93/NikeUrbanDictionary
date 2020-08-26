package com.example.urbandictionary.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.urbandictionary.R
import com.example.urbandictionary.data.model.Define
import com.example.urbandictionary.data.model.ResponseUrban
import com.example.urbandictionary.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.activity_search_landing.*

class SearchLandingActivity : AppCompatActivity() {

    private var currentTerm: String = ""
    private var currentList: List<Define>? = null
    private lateinit var currentAdapter: ResultAdapter
    private lateinit var urbanViewModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_landing)
        urbanViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(SearchViewModel::class.java)
        rvDefinitions.adapter = ResultAdapter(results = listOf())
        rvDefinitions.layoutManager = LinearLayoutManager(this)
        urbanViewModel.termLiveData.observe(this, definitionObserver)
    }

    private val definitionObserver by lazy {
        Observer<SearchViewModel.Resource<ResponseUrban, String>> { response ->
            visibilitySwitch(true)
            response.data?.list?.let {
                currentList = it
                currentAdapter = ResultAdapter(it)
                rvDefinitions.adapter = currentAdapter
                if (it.isNotEmpty() && tvSortDown.visibility == View.INVISIBLE && tvSortUp.visibility == View.INVISIBLE) {
                    tvSortDown.visibility = View.VISIBLE
                    tvSortUp.visibility = View.VISIBLE
                } else if (it.isEmpty() && tvSortDown.visibility == View.VISIBLE && tvSortUp.visibility == View.VISIBLE) {
                    tvSortDown.visibility = View.INVISIBLE
                    tvSortUp.visibility = View.INVISIBLE
                }
            }
            response.errorMessage?.let {
                AlertDialog.Builder(this).apply {
                    setTitle(R.string.error)
                    setMessage(it)
                    setPositiveButton(android.R.string.ok, null)
                }.show()
            }
        }
    }

    fun onClick(view: View) {
        when (view.id) {
            btnSearch.id -> {
                if (tieEntry.text.isNullOrEmpty().not()) {
                    visibilitySwitch(false)
                    with(tieEntry.text.toString()) {
                        if (currentTerm == this && rvDefinitions.adapter?.itemCount != 0) {
                            makeLongToast("You already have the results for this word listed.")
                        } else {
                            urbanViewModel.getSearchData(this)
                            currentTerm = this
                        }
                    }
                } else makeLongToast("If you would like to search, please type a word.")
            }
            btnClear.id -> {
                ResultAdapter(listOf()).let {
                    currentAdapter = it
                    rvDefinitions.adapter = it
                }
                if (tvSortUp.visibility == View.VISIBLE)
                    tvSortUp.visibility = View.INVISIBLE
                if (tvSortDown.visibility == View.VISIBLE)
                    tvSortDown.visibility = View.INVISIBLE
                tieEntry.setText("")
            }
            tvSortUp.id -> sortAdapter(true)
            else -> sortAdapter(false)
        }
    }

    private fun sortAdapter(up: Boolean) =
        if (up)
            rvDefinitions.adapter =
                ResultAdapter((currentList as List<Define>).sortedByDescending { it.thumbsUp })
        else
            rvDefinitions.adapter =
                ResultAdapter((currentList as List<Define>).sortedByDescending { it.thumbsDown })


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
