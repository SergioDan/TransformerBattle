package com.sergiodan.transformerbattle.di

import com.google.gson.Gson
import com.sergiodan.transformerbattle.data.services.TransformerService
import dagger.Lazy
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ServicesModule {

    @Provides
    @Singleton
    fun provideTransformerService(
        client: Lazy<OkHttpClient>,
        gson: Gson
    ): TransformerService {
        return Retrofit.Builder()
            .baseUrl(TransformerService.END_POINT)
            .callFactory(client.get())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(TransformerService::class.java)
    }
}