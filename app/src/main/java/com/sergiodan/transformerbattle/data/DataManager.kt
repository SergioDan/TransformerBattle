package com.sergiodan.transformerbattle.data

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.MutableLiveData
import com.github.ajalt.timberkt.d
import com.github.ajalt.timberkt.e
import com.sergiodan.transformerbattle.data.dispatcher.CoroutinesThreadProvider
import com.sergiodan.transformerbattle.data.model.Result
import com.sergiodan.transformerbattle.data.model.Transformer
import com.sergiodan.transformerbattle.data.repository.TransformersRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

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

    companion object {
        const val AUTH_TOKEN = "AUTH_TOKEN"
    }
}