package com.sergiodan.transformerbattle.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sergiodan.transformerbattle.ui.views.OnSliderChangeCallback
import com.sergiodan.transformerbattle.ui.views.TechnicalSpecification
import com.sergiodan.transformerbattle.ui.views.TechnicalSpecificationViewHolder

class TechnicalSpecificationAdapter(var list: List<TechnicalSpecification>,
                                    val onSliderChangeCallback: OnSliderChangeCallback):
    RecyclerView.Adapter<TechnicalSpecificationViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TechnicalSpecificationViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return TechnicalSpecificationViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: TechnicalSpecificationViewHolder,
                                  position: Int) {
        val specification = list[position]
        holder.bind(specification, onSliderChangeCallback)
    }

    override fun getItemCount(): Int = list.size
}