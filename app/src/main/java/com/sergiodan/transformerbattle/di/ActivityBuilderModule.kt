package com.sergiodan.transformerbattle.di

import com.sergiodan.transformerbattle.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class ActivityBuildersModule {
    @ContributesAndroidInjector()
    abstract fun contributeMainActivity(): MainActivity?
}