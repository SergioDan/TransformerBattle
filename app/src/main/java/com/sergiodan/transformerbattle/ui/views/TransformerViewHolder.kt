package com.sergiodan.transformerbattle.ui.views

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sergiodan.transformerbattle.R
import com.sergiodan.transformerbattle.data.model.Transformer

class TransformerViewHolder(inflater: LayoutInflater, parent: ViewGroup):
    RecyclerView.ViewHolder(inflater.inflate(R.layout.view_holder_transformer, parent, false)) {

    private var nameTextView: TextView
    private var imageTeamView: ImageView

    init {
        nameTextView = itemView.findViewById(R.id.tv_name)
        imageTeamView = itemView.findViewById(R.id.iv_alliance)
    }

    fun bind(transformer: Transformer) {
        nameTextView.text = transformer.name
        Glide
            .with(itemView.context)
            .load(transformer.teamIcon)
            .into(imageTeamView)
        
    }


}