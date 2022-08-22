package com.firozpoc.weatherforecastandroidapplication.di

import com.firozpoc.weatherforecastandroidapplication.api.ApiService
import com.firozpoc.weatherforecastandroidapplication.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun providesBaseUrl() = Constants.BASE_URL

    @Provides
    @Singleton
    fun providesRetrofitInstance(BASE_URL : String) : ApiService =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
}