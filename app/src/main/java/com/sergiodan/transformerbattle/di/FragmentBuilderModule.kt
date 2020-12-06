package com.sergiodan.transformerbattle.di

import com.sergiodan.transformerbattle.ui.fragments.BotsFragment
import com.sergiodan.transformerbattle.ui.fragments.CreateTransformerFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun provideBotsFragment(): BotsFragment?

    @ContributesAndroidInjector
    abstract fun provideCreateTransformerFragment(): CreateTransformerFragment?
}