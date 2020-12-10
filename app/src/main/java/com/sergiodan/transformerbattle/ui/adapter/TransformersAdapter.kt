package com.sergiodan.transformerbattle.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sergiodan.transformerbattle.data.model.Transformer
import com.sergiodan.transformerbattle.ui.views.TransformerViewHolder

class TransformersAdapter(var list: List<Transformer>): RecyclerView.Adapter<TransformerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransformerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return TransformerViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: TransformerViewHolder, position: Int) {
        val transformer = list[position]
        holder.bind(transformer)
    }

    override fun getItemCount(): Int = list.size

    fun updateList(newList: List<Transformer>) {
        this.list = newList
        notifyDataSetChanged()
    }
}