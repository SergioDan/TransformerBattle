package com.sergiodan.transformerbattle.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.ajalt.timberkt.d
import com.sergiodan.transformerbattle.R
import com.sergiodan.transformerbattle.data.model.toMap
import com.sergiodan.transformerbattle.databinding.FragmentBotsBinding
import com.sergiodan.transformerbattle.ui.adapter.TechnicalSpecificationAdapter
import com.sergiodan.transformerbattle.ui.adapter.TransformersAdapter
import com.sergiodan.transformerbattle.ui.getColor
import com.sergiodan.transformerbattle.ui.viewmodel.MainViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class BotsFragment: DaggerFragment() {

    @Inject
    lateinit var mainViewModel: MainViewModel

    private var autobotsAdapter: TransformersAdapter = TransformersAdapter(listOf())
    private var decepticonsAdapter: TransformersAdapter = TransformersAdapter(listOf())
    private lateinit var binding: FragmentBotsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentBotsBinding.inflate(inflater, container, false)
        startRequest()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        binding.llFabBrawl.setOnClickListener {
            Toast.makeText(requireContext(), "Implementation still in progress", Toast.LENGTH_LONG).show()
        }

        binding.btCreate.setOnClickListener {
            findNavController()
                .navigate(BotsFragmentDirections.actionFragmentBotsToFragmentCreateTransformer())
        }

        binding.toolbar.setTitleTextColor(requireContext().getColor(arrayOf(R.attr.textColor)))
    }

    private fun setRecyclerView() {
        val autoRecyclerView = binding.rvAutobots
        autoRecyclerView.layoutManager = LinearLayoutManager(activity)
        autoRecyclerView.adapter = autobotsAdapter

        val decepticonRecyclerView = binding.rvDecepticons
        decepticonRecyclerView.layoutManager = LinearLayoutManager(activity)
        decepticonRecyclerView.adapter = decepticonsAdapter
    }

    private fun startRequest() {
        mainViewModel.getTransformers()
        mainViewModel.transformers.observe(viewLifecycleOwner, Observer { list ->
            list.forEach {
                d { "Transformer=${it.toMap()}" }
            }
            val autobots = list.filter { it.team == "A" }
            val decepticons = list.filter { it.team == "D" }
            autobotsAdapter.updateList(autobots)
            decepticonsAdapter.updateList(decepticons)
        })
    }
}