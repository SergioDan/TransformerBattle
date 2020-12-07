package com.sergiodan.transformerbattle.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergiodan.transformerbattle.data.DataManager
import com.sergiodan.transformerbattle.data.model.Transformer
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject internal constructor(): ViewModel() {

    @Inject
    lateinit var dataManager: DataManager

    private var _transformersLiveData = MutableLiveData<List<Transformer>>().also {
        it.value = listOf()
    }

    val transformers: LiveData<List<Transformer>> = _transformersLiveData

    fun getTransformers() {
        viewModelScope.launch {
            val result = dataManager.getTransformers()
            _transformersLiveData.postValue(result)
        }
    }
}