package com.sergiodan.transformerbattle.di

import android.app.Application
import com.sergiodan.transformerbattle.TransformerBattleApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class,
    AppModule::class,
//    ActivityBuildersModule::class,
    ActivityModule::class,
    ServicesModule::class,
    DataManagerModule::class,
    FragmentBuildersModule::class
])
interface AppComponent: AndroidInjector<TransformerBattleApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application:Application): Builder?
        fun build(): AppComponent?
    }
}