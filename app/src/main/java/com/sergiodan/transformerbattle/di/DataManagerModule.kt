package com.sergiodan.transformerbattle.di

import com.sergiodan.transformerbattle.data.DataManager
import com.sergiodan.transformerbattle.data.datasources.TransformersRemoteDataSource
import com.sergiodan.transformerbattle.data.dispatcher.CoroutinesThreadProvider
import com.sergiodan.transformerbattle.data.repository.TransformersRepository
import com.sergiodan.transformerbattle.data.services.TransformerService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataManagerModule {

    @Provides
    @Singleton
    fun provideTransformerRemoteDataSource(
        service: TransformerService
    ): TransformersRemoteDataSource {
        return TransformersRemoteDataSource(service)
    }

    @Provides
    @Singleton
    fun provideTransformerRepository(
        dataSource: TransformersRemoteDataSource
    ): TransformersRepository {
        return TransformersRepository(dataSource)
    }

    @Provides
    @Singleton
    fun provideDataManager(
        repository: TransformersRepository,
        coroutinesThreadProvider: CoroutinesThreadProvider
    ): DataManager {
        return DataManager(repository, coroutinesThreadProvider)
    }
}