package com.sergiodan.transformerbattle.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergiodan.transformerbattle.data.DataManager
import com.sergiodan.transformerbattle.data.model.Transformer
import kotlinx.coroutines.launch
import javax.inject.Inject

class CreateTransformerViewModel @Inject internal constructor(): ViewModel() {

    @Inject
    lateinit var dataManager: DataManager

    private var _transformerLiveData = MutableLiveData<Transformer?>().also {
        it.value = null
    }

    val transformer: LiveData<Transformer?> = _transformerLiveData

    fun createTransformer(transformer: Transformer) {
        viewModelScope.launch {
            val transformer = dataManager.createTransformer(transformer)
            _transformerLiveData.postValue(transformer)
        }
    }
}