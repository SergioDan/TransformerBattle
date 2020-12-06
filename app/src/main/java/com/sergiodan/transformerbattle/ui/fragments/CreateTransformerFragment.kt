package com.sergiodan.transformerbattle.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.sergiodan.transformerbattle.R
import com.sergiodan.transformerbattle.databinding.FragmentCreateTransformerBinding
import com.sergiodan.transformerbattle.ui.adapter.TechnicalSpecificationAdapter
import com.sergiodan.transformerbattle.ui.views.TechnicalSpecification
import dagger.android.support.DaggerFragment

class CreateTransformerFragment: DaggerFragment() {

    private lateinit var binding: FragmentCreateTransformerBinding
    private lateinit var specificationsAdapter: TechnicalSpecificationAdapter
    private var list = specificationsList

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentCreateTransformerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
    }

    private fun setRecyclerView() {
        val recyclerView = binding.rvSpecifications
        recyclerView.layoutManager = LinearLayoutManager(activity)
        specificationsAdapter = TechnicalSpecificationAdapter(list, this::onSliderValueChange)
        recyclerView.adapter = specificationsAdapter
    }

    private fun onSliderValueChange(spec: Int, newValue: Int) {
        list = list.map {
            if (it.name == spec) {
                it.copy(score = newValue)
            } else {
                it
            }
        }
    }

    companion object {
        private const val DEFAULT_SCORE = 5
        val specificationsList = listOf(
            TechnicalSpecification(R.string.strength, DEFAULT_SCORE),
            TechnicalSpecification(R.string.intelligence, DEFAULT_SCORE),
            TechnicalSpecification(R.string.speed, DEFAULT_SCORE),
            TechnicalSpecification(R.string.endurance, DEFAULT_SCORE),
            TechnicalSpecification(R.string.rank, DEFAULT_SCORE),
            TechnicalSpecification(R.string.courage, DEFAULT_SCORE),
            TechnicalSpecification(R.string.firepower, DEFAULT_SCORE),
            TechnicalSpecification(R.string.skill, DEFAULT_SCORE)
        )
    }
}