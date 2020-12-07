package com.sergiodan.transformerbattle.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.ajalt.timberkt.d
import com.sergiodan.transformerbattle.data.model.toMap
import com.sergiodan.transformerbattle.databinding.FragmentBotsBinding
import com.sergiodan.transformerbattle.ui.adapter.TechnicalSpecificationAdapter
import com.sergiodan.transformerbattle.ui.adapter.TransformersAdapter
import com.sergiodan.transformerbattle.ui.viewmodel.MainViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class BotsFragment: DaggerFragment() {

    @Inject
    lateinit var mainViewModel: MainViewModel

    private lateinit var adapter: TransformersAdapter
    private lateinit var binding: FragmentBotsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentBotsBinding.inflate(inflater, container, false)

        binding.llFabBrawl.setOnClickListener {
            findNavController()
                    .navigate(BotsFragmentDirections.actionFragmentBotsToFragmentCreateTransformer())
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        startRequest()
    }

    private fun setRecyclerView() {
        val recyclerView = binding.rvBots
        recyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = TransformersAdapter(listOf())
        recyclerView.adapter = adapter
    }

    private fun startRequest() {
        mainViewModel.getTransformers()
        mainViewModel.transformers.observe(viewLifecycleOwner, Observer {
            it.forEach {
                d { "Transformer=${it.toMap()}" }
            }
            adapter.updateList(it)
        })
    }
}