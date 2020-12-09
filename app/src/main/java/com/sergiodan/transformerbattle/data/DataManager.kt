package com.sergiodan.transformerbattle.data

import android.content.SharedPreferences
import androidx.core.content.edit
import com.github.ajalt.timberkt.d
import com.github.ajalt.timberkt.e
import com.sergiodan.transformerbattle.data.dispatcher.CoroutinesThreadProvider
import com.sergiodan.transformerbattle.data.model.BrawlResult
import com.sergiodan.transformerbattle.data.model.Result
import com.sergiodan.transformerbattle.data.model.Transformer
import com.sergiodan.transformerbattle.data.model.getOverallRating
import com.sergiodan.transformerbattle.data.repository.TransformersRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Inject
import kotlin.math.abs
import kotlin.math.min

/**
 * Man-in-the-middle class for handling the requests to the external source
 */
class DataManager @Inject constructor(private val transformerRepository: TransformersRepository,
                                      private val sharedPreferences: SharedPreferences,
                                         private val dispatcherProvider: CoroutinesThreadProvider) {
    private val parentJob = SupervisorJob()
    private val scope = CoroutineScope(dispatcherProvider.computation + parentJob)

    /**
     * For authentication
     */
    private var authToken: String? = sharedPreferences.getString(AUTH_TOKEN, null)
        set(value) {
            sharedPreferences.edit { putString(AUTH_TOKEN, value) }
            field = value
        }

    suspend fun getTransformers(): List<Transformer> {
        return getToken()?.let {
            return when (val result = transformerRepository.getTransformers(it)) {
                is Result.Success -> {
                    result.data
                }
                is Result.Error -> {
                    e(result.exception) { "Error getting the data." }
                    listOf()
                }
            }
        } ?: run {
            listOf<Transformer>()
        }
    }

    suspend fun getToken(): String? {
        authToken?.let {
            d { "nothing to do here" }
        } ?: run {
            when (val result = transformerRepository.retrieveToken()) {
                is Result.Success -> {
                    authToken = result.data
                }
                is Result.Error -> {
                    e(result.exception) { "Error retrieving token" }
                }
            }
        }

        return authToken
    }

    suspend fun createTransformer(transformer: Transformer): Transformer? {
        return getToken()?.let {
            return when (val result = transformerRepository.createTransformer(it, transformer)) {
                is Result.Success -> {
                    result.data
                }
                is Result.Error -> {
                    e(result.exception) { "Error creating transformer" }
                    null
                }
            }
        } ?: run {
            null
        }
    }

    suspend fun deleteTransformer(transformer: Transformer): Boolean {
        return getToken()?.let {
            return when(val result = transformerRepository.deleteTransformer(it, transformer.id ?: "")) {
                is Result.Success -> {
                    result.data
                }
                is Result.Error -> {
                    e(result.exception) { "Error creating transformer" }
                    false
                }
            }
        } ?: run {
            false
        }
    }

    suspend fun updateTransformer(transformer: Transformer): Transformer? {
        return getToken()?.let {
            return when(val result = transformerRepository.updateTransformer(it, transformer)) {
                is Result.Success -> {
                    result.data
                }
                is Result.Error -> {
                    e(result.exception) { "Error creating transformer" }
                    null
                }
            }
        } ?: run {
            null
        }
    }

    suspend fun brawlRoutine(autobots: List<Transformer>, decepticons: List<Transformer>): BrawlResult {
        val brawlResult = brawl(autobots, decepticons)
        brawlResult.defeated.forEach {
            deleteTransformer(it)
        }
        return brawlResult
    }

    companion object {
        private fun getDefeatedTransformers(sortedAutobots: List<Transformer>, sortedDecepticons: List<Transformer>): List<Transformer> {
            val min = min(sortedAutobots.size, sortedDecepticons.size)
            val defeated = mutableListOf<Transformer>()
            for (i in 0 until min) {
                val autobot = sortedAutobots[i]
                val decepticon = sortedDecepticons[i]

                val courage = autobot.courage - decepticon.courage
                val strength = autobot.strength - decepticon.courage
                val skill = autobot.skill - decepticon.skill
                val overall = autobot.getOverallRating() - decepticon.getOverallRating()

                var shouldBreak = false
                when {
                    ((autobot.name.equals(AUTOBOT_NAME, true) || decepticon.name.equals(AUTOBOT_NAME, true)) &&
                            (autobot.name.equals(DECEPTICON_NAME, true) || decepticon.name.equals(
                                DECEPTICON_NAME, true))) -> {
                        shouldBreak = true
                    }
                    (autobot.name.equals(AUTOBOT_NAME, true)
                            || autobot.name.equals(DECEPTICON_NAME, true)) -> {
                        defeated.add(decepticon)
                    }
                    (decepticon.name.equals(DECEPTICON_NAME, true)
                            || decepticon.name.equals(AUTOBOT_NAME, true)) -> {
                        defeated.add(autobot)
                    }
                    (abs(courage) >= 4 && abs(strength) >= 3)-> {
                        val transformer = if (courage > 0 && strength > 0) {
                            decepticon
                        } else {
                            autobot
                        }
                        defeated.add(transformer)
                    }
                    (abs(skill) >= 3) -> {
                        val transformer = if (skill > 0) {
                            decepticon
                        } else {
                            autobot
                        }
                        defeated.add(transformer)
                    }
                    (overall > 0) -> {
                        defeated.add(decepticon)
                    }
                    else -> {
                        defeated.add(autobot)
                    }
                }

                if (shouldBreak) {
                    defeated.clear()
                    defeated.addAll(sortedAutobots)
                    defeated.addAll(sortedDecepticons)
                    break
                }
            }

            return defeated
        }

        /**
         * return list of defeated transformers,
         *        list of winning one,
         *        identifier for winning team
         */
        fun brawl(autobots: List<Transformer>, decepticons: List<Transformer>): BrawlResult {
            val sortedAutobots = autobots.sortedByDescending { it.rank }
            val sortedDecepticons = decepticons.sortedByDescending { it.rank }

            val defeated = getDefeatedTransformers(sortedAutobots, sortedDecepticons)

            val defeatedAutobots = defeated.filter { it.team.equals(AUTOBOT_TEAM_IDENTIFIER, true) }
            val defeatedDecepticons = defeated.filter { it.team.equals(DECEPTICON_TEAM_IDENTIFIER, true) }

            return when {
                (defeatedAutobots.size > defeatedDecepticons.size) -> {
                    val standing = sortedDecepticons.toMutableList().apply {
                        this.removeAll(defeatedDecepticons)
                    }
                    BrawlResult(defeated, standing.toList(), DECEPTICON_TEAM_IDENTIFIER)
                }
                (defeatedAutobots.size < defeatedDecepticons.size) -> {
                    val standing = sortedAutobots.toMutableList().apply {
                        this.removeAll(defeatedAutobots)
                    }
                    BrawlResult(defeated, standing.toList(), AUTOBOT_TEAM_IDENTIFIER)
                }
                else -> {
                    BrawlResult(defeated, listOf(), "")
                }
            }
        }
    }
}