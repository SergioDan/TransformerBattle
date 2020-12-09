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
import com.google.gson.Gson
import com.sergiodan.transformerbattle.R
import com.sergiodan.transformerbattle.data.AUTOBOT_TEAM_IDENTIFIER
import com.sergiodan.transformerbattle.data.DECEPTICON_TEAM_IDENTIFIER
import com.sergiodan.transformerbattle.data.model.BrawlResult
import com.sergiodan.transformerbattle.data.model.toMap
import com.sergiodan.transformerbattle.databinding.FragmentBotsBinding
import com.sergiodan.transformerbattle.ui.adapter.TechnicalSpecificationAdapter
import com.sergiodan.transformerbattle.ui.adapter.TransformersAdapter
import com.sergiodan.transformerbattle.ui.getColor
import com.sergiodan.transformerbattle.ui.viewmodel.MainViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject
import kotlin.math.min

class BotsFragment: DaggerFragment() {

    @Inject
    lateinit var mainViewModel: MainViewModel

    @Inject
    lateinit var gson: Gson

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
            mainViewModel.brawl(autobotsAdapter.list, decepticonsAdapter.list)
            mainViewModel.brawlResult.observe(viewLifecycleOwner, Observer {
                it?.let {
//                    Toast.makeText(requireContext(),
//                        "Winning team = ${if (it.winningTeamId == AUTOBOT_TEAM_IDENTIFIER) { "Autobots" } else if (it.winningTeamId == DECEPTICON_TEAM_IDENTIFIER) { "Decepticons" } else { "No winning team"} }",
//                        Toast.LENGTH_LONG).show()
                    val battles = min(autobotsAdapter.list.size, decepticonsAdapter.list.size)
                    showCompleteDialog(battles, it)
                }
            })
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

    private fun showCompleteDialog(battles: Int, result: BrawlResult) {
        CompletedBattleDialog
            .newInstance(battles, gson.toJson(result))
            .show(this.parentFragmentManager, CompletedBattleDialog.TAG)
    }
}