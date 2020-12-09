package com.sergiodan.transformerbattle.ui.fragments

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sergiodan.transformerbattle.R
import com.sergiodan.transformerbattle.data.model.Transformer
import com.sergiodan.transformerbattle.data.model.specsToTransformer
import com.sergiodan.transformerbattle.databinding.FragmentCreateTransformerBinding
import com.sergiodan.transformerbattle.ui.adapter.TechnicalSpecificationAdapter
import com.sergiodan.transformerbattle.ui.getColor
import com.sergiodan.transformerbattle.ui.viewmodel.CreateTransformerViewModel
import com.sergiodan.transformerbattle.ui.views.TechnicalSpecification
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class CreateTransformerFragment: DaggerFragment() {

    @Inject
    lateinit var viewModel: CreateTransformerViewModel

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

        ContextCompat.getDrawable(requireContext(), R.drawable.ic_close)?.let { drawable ->
            binding.toolbar.navigationIcon = DrawableCompat.wrap(drawable).also {
                DrawableCompat.setTint(
                    it,
                    requireContext().getColor(arrayOf(R.attr.textColor))
                )
            }
        }
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        setRecyclerView()
        binding.btCreate.setOnClickListener {
            val transformer = transformSpecListToClass().copy(
                name = binding.etName.text.toString(),
                team = when {
                    binding.rbAutobot.isChecked -> { "A" }
                    binding.rbDecepticon.isChecked -> { "D" }
                    else -> { "" }
                }
            )
            viewModel.createTransformer(transformer)
            viewModel.transformer.observe(viewLifecycleOwner, Observer {
                it?.let {
                    findNavController().popBackStack()
                }
            })
        }
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

    private fun transformSpecListToClass(): Transformer {
        val specsMap = mutableMapOf<String, Int>()

        list.forEach {
            specsMap[requireContext().getString(it.name)] = it.score
        }

        return specsMap.specsToTransformer()
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