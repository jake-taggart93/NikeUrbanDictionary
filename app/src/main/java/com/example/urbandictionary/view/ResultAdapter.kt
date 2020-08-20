package com.example.urbandictionary.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.urbandictionary.R
import com.example.urbandictionary.data.model.Define
import kotlinx.android.synthetic.main.list_item_definition.view.*

class ResultAdapter(private val results: List<Define>): RecyclerView.Adapter<ResultAdapter.ResultHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ResultHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_definition, parent, false))

    override fun onBindViewHolder(holder: ResultHolder, position: Int) =
        results[position].let {
            holder.itemView.tvDefinition.text = it.definition
            holder.itemView.tvThumbsUp.text = it.thumbsUp.toString()
            holder.itemView.tvThumbsDn.text = it.thumbsDown.toString()
        }

     override fun getItemCount(): Int = results.size

    class ResultHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}