package com.vvz.brewbird.di

import com.vvz.brewbird.data.remote.BreweryApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
open class Api {

    @Provides
    fun providesBreweryApi(retrofit: Retrofit): BreweryApi = retrofit.create(BreweryApi::class.java)

}