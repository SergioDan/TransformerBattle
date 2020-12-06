package com.sergiodan.transformerbattle.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.github.ajalt.timberkt.d
import com.sergiodan.transformerbattle.data.model.toMap
import com.sergiodan.transformerbattle.databinding.FragmentBotsBinding
import com.sergiodan.transformerbattle.ui.viewmodel.MainViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class BotsFragment: DaggerFragment() {

    @Inject
    lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val binding = FragmentBotsBinding.inflate(inflater, container, false)

        mainViewModel.getTransformers()
        mainViewModel.transformers.observe(viewLifecycleOwner, Observer {
            it.forEach {
                d { "Transformer=${it.toMap()}" }
            }
        })

        binding.llFabBrawl.setOnClickListener {
            findNavController()
                    .navigate(BotsFragmentDirections.actionFragmentBotsToFragmentCreateTransformer())
        }

        return binding.root
    }
}