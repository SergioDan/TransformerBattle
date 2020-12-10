package com.sergiodan.transformerbattle.ui.views

import android.graphics.PointF
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sergiodan.transformerbattle.R
import com.sergiodan.transformerbattle.data.model.Transformer
import com.sergiodan.transformerbattle.data.model.getOverallRating
import com.sergiodan.transformerbattle.ui.fragments.CreateTransformerFragment

private val labels by lazy {
    listOf(
        R.string.strength,
        R.string.intelligence,
        R.string.speed,
        R.string.endurance,
        R.string.rank,
        R.string.courage,
        R.string.firepower,
        R.string.skill
    )
}

class TransformerViewHolder(inflater: LayoutInflater, parent: ViewGroup):
    RecyclerView.ViewHolder(inflater.inflate(R.layout.view_holder_transformer, parent, false)) {

    private var nameTextView: TextView
    private var imageTeamView: ImageView
    private var chartView: LineChartView
    private var overallTextView: TextView

    init {
        nameTextView = itemView.findViewById(R.id.tv_name)
        imageTeamView = itemView.findViewById(R.id.iv_alliance)
        chartView = itemView.findViewById(R.id.lcv_chart)
        overallTextView = itemView.findViewById(R.id.tv_overall)
    }

    fun bind(transformer: Transformer) {
        nameTextView.text = transformer.name
        Glide
            .with(itemView.context)
            .load(transformer.teamIcon)
            .into(imageTeamView)

        getYCoordinates(transformer).apply {
            chartView.setCoordinatesAndLabels(this.toTypedArray(), labels.toTypedArray())
        }

        overallTextView.text = itemView.context.getString(R.string.overall, transformer.getOverallRating())
    }


    private fun getYCoordinates(transformer: Transformer): List<Float> {
        val listSpecs = mutableListOf<Float>()
        listSpecs.add(calculateCoordinateX(transformer.strength))
        listSpecs.add(calculateCoordinateX(transformer.intelligence))
        listSpecs.add(calculateCoordinateX(transformer.speed))
        listSpecs.add(calculateCoordinateX(transformer.endurance))
        listSpecs.add(calculateCoordinateX(transformer.rank))
        listSpecs.add(calculateCoordinateX(transformer.courage))
        listSpecs.add(calculateCoordinateX(transformer.firepower))
        listSpecs.add(calculateCoordinateX(transformer.skill))
        return listSpecs
    }

    private fun calculateCoordinateX(score: Int): Float {
        return 80f - ((score.toFloat()/10f)*80f)
    }


}