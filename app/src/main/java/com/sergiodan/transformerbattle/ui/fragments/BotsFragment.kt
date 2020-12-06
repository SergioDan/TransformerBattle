package com.sergiodan.transformerbattle.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.sergiodan.transformerbattle.databinding.FragmentBotsBinding
import dagger.android.support.DaggerFragment

class BotsFragment: DaggerFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val binding = FragmentBotsBinding.inflate(inflater, container, false)

        binding.llFabBrawl.setOnClickListener {
            findNavController()
                    .navigate(BotsFragmentDirections.actionFragmentBotsToFragmentCreateTransformer())
        }

        return binding.root
    }
}