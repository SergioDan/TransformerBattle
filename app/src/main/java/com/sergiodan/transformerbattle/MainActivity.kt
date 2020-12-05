package com.sergiodan.transformerbattle

import android.os.Bundle
import androidx.lifecycle.Observer
import com.github.ajalt.timberkt.d
import com.sergiodan.transformerbattle.data.model.toMap
import com.sergiodan.transformerbattle.ui.viewmodel.MainViewModel
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainViewModel.retrieveToken()
    }

    override fun onStart() {
        super.onStart()
        mainViewModel.getTransformers()
        mainViewModel.transformers.observe(this, Observer {
            it.forEach {
                d { "Transformer=${it.toMap()}" }
            }
        })
    }
}