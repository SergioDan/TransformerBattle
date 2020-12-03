package com.sergiodan.transformerbattle

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.ajalt.timberkt.e
import com.sergiodan.transformerbattle.data.dispatcher.CoroutinesThreadProvider
import com.sergiodan.transformerbattle.data.model.Result
import com.sergiodan.transformerbattle.data.model.Transformer
import com.sergiodan.transformerbattle.data.repository.TransformersRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Man-in-the-middle for handling the requests to the external source
 */
class DataManager @Inject constructor(private val transformerRepository: TransformersRepository,
                                         private val dispatcherProvider: CoroutinesThreadProvider) {
    private val parentJob = SupervisorJob()
    private val scope = CoroutineScope(dispatcherProvider.computation + parentJob)

    private val transformersLiveData: MutableLiveData<List<Transformer>> = MutableLiveData()

    fun getTransformers(): LiveData<List<Transformer>> {
        scope.launch {
            val result = transformerRepository.getTransformers()
            when(result) {
                is Result.Success -> {
                    transformersLiveData.postValue(result.data)
                }
                is Result.Error ->  {
                    e(result.exception){"no vi ni el ponque"}
                }
            }

        }
        return transformersLiveData
    }
}