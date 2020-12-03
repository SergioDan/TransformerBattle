package com.sergiodan.transformerbattle

import com.sergiodan.transformerbattle.di.AppComponent
import com.sergiodan.transformerbattle.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import timber.log.Timber

class TransformerBattleApplication: DaggerApplication() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication?>? {
        daggerAppComponent = DaggerAppComponent.builder()
            .application(this)
            ?.build()
        return daggerAppComponent
    }

    companion object {
        var daggerAppComponent: AppComponent? = null
        fun getAppComponent() = daggerAppComponent
    }
}