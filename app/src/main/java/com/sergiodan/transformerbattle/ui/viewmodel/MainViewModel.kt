package com.sergiodan.transformerbattle.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergiodan.transformerbattle.data.DataManager
import com.sergiodan.transformerbattle.data.model.BrawlResult
import com.sergiodan.transformerbattle.data.model.Transformer
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject internal constructor(): ViewModel() {

    @Inject
    lateinit var dataManager: DataManager

    private var _transformersLiveData = MutableLiveData<List<Transformer>>().also {
        it.value = listOf()
    }

    private var _resultLiveData = MutableLiveData<BrawlResult?>().also {
        it.value = null
    }

    val transformers: LiveData<List<Transformer>> = _transformersLiveData
    val brawlResult: LiveData<BrawlResult?> = _resultLiveData

    fun getTransformers() {
        viewModelScope.launch {
            val result = dataManager.getTransformers()
            _transformersLiveData.postValue(result)
        }
    }

    fun brawl(autobots: List<Transformer>, decepticons: List<Transformer>) {
        viewModelScope.launch {
            val result = dataManager.brawlRoutine(autobots, decepticons)
            _resultLiveData.postValue(result)
        }
    }
}