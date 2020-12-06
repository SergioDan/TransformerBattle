package com.sergiodan.transformerbattle.ui.views

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sergiodan.transformerbattle.R

typealias OnSliderChangeCallback = (spec:Int, newValue: Int) -> Unit

data class TechnicalSpecification(val name: Int, val score: Int)

class SeekListener(private val specId: Int, private val onChangeCallback: OnSliderChangeCallback): SeekBar.OnSeekBarChangeListener {
    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
        onChangeCallback.invoke(specId, p1)
    }

    override fun onStartTrackingTouch(p0: SeekBar?) { }
    override fun onStopTrackingTouch(p0: SeekBar?) { }
}

class TechnicalSpecificationViewHolder(inflater: LayoutInflater,parent: ViewGroup):
        RecyclerView.ViewHolder(inflater.inflate(R.layout.view_tech_spec, parent, false)) {

    private var specificationNameTextView: TextView
    private var scoreSlider: SeekBar
    private var scoreTextView: TextView

    var onChangeCallback: OnSliderChangeCallback? = null

    init {
        specificationNameTextView = itemView.findViewById(R.id.tv_spec_name)
        scoreSlider = itemView.findViewById(R.id.sb_score)
        scoreTextView = itemView.findViewById(R.id.tv_score)
    }

    fun bind(techSpec: TechnicalSpecification) {

        specificationNameTextView.text = itemView.context.getString(techSpec.name)
        scoreSlider.progress = techSpec.score
        scoreTextView.text = "${techSpec.score}"

        scoreSlider.setOnSeekBarChangeListener(SeekListener(techSpec.name, this::onSeekBarChange))
    }

    private fun onSeekBarChange(specId: Int, newValue: Int) {
        scoreTextView.text = "$newValue"
        onChangeCallback?.invoke(specId, newValue)
    }
}