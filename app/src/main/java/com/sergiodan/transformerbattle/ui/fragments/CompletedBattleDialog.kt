package com.sergiodan.transformerbattle.ui.fragments

import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.vectordrawable.graphics.drawable.Animatable2Compat
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.github.ajalt.timberkt.d
import com.google.gson.Gson
import com.sergiodan.transformerbattle.R
import com.sergiodan.transformerbattle.data.AUTOBOT_TEAM_IDENTIFIER
import com.sergiodan.transformerbattle.data.model.BrawlResult
import com.sergiodan.transformerbattle.databinding.FragmentBrawlResultDialogBinding
import java.util.*


class CompletedBattleDialog: DialogFragment() {

    private val handler = Handler()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentBrawlResultDialogBinding.inflate(inflater, container, false)

        setupView(binding)
        setupClickListeners(binding)

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    private fun setupView(binding: FragmentBrawlResultDialogBinding) {
        val battles = arguments?.getInt(KEY_NUMBER_BATTLES)
        val result: BrawlResult = Gson().fromJson(arguments?.getString(KEY_BATTLE_RESULT), BrawlResult::class.java)

        val anim = if (result.winningTeamId == AUTOBOT_TEAM_IDENTIFIER) {
            R.drawable.anim_decepticno
        } else {
            R.drawable.anim_autobot
        }

        val animatedVectorDrawableCompat =
            AnimatedVectorDrawableCompat.create(requireContext(), anim)
        binding.ivWinnerLogo.setImageDrawable(animatedVectorDrawableCompat)
        handler.postDelayed({
            animatedVectorDrawableCompat?.start()
        }, 1000)

        val losingTeam = result.survivorsLosingTeam.map { it.name }.joinToString(", ")
        val winningTeam = result.winning.map { it.name }.joinToString(", ")

        binding.tvDetails.text = requireContext()
            .getString(R.string.result_description,
                battles,
                winningTeam,
                losingTeam)
    }

    private fun setupClickListeners(binding: FragmentBrawlResultDialogBinding) {
        binding.root.setOnClickListener {
            dismiss()
        }
    }

    companion object {
        const val TAG = "CompletedBattleDialog"
        private const val KEY_BATTLE_RESULT = "KEY_BATTLE_RESULT"
        private const val KEY_NUMBER_BATTLES = "KEY_NUMBER_BATTLES"

        fun newInstance(numberOfBattles: Int, resultJson: String): CompletedBattleDialog {
            val args = Bundle()
            args.putInt(KEY_NUMBER_BATTLES, numberOfBattles)
            args.putString(KEY_BATTLE_RESULT, resultJson)
            val fragment = CompletedBattleDialog()
            fragment.arguments = args
            return fragment
        }
    }
}