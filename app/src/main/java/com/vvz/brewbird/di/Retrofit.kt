package com.vvz.brewbird.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.vvz.brewbird.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
open class Retrofit {

    protected open val baseUrl: String = BuildConfig.BREWERIES_BASE_URL
    protected open val connectionTimeout: Long = 10
    protected open val readTimeout: Long = 10

    @Provides
    fun provideRetrofit(httpClient: OkHttpClient,
                        jsonConverter: Converter.Factory): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(baseUrl)
            .client(httpClient)
            .addConverterFactory(jsonConverter)
            .build()
    }


    @Provides
    fun provideHttpClient(loggingInterceptor: Interceptor): OkHttpClient {
        return OkHttpClient
            .Builder()
            .connectTimeout(connectionTimeout, TimeUnit.SECONDS)
            .readTimeout(readTimeout, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()
    }


    @Provides
    fun provideLoggingInterceptor(): Interceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }


    @Provides
    fun provideMoshiConverter(): Moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()


    @Provides
    fun provideJsonConverterFactory(moshiConverter: Moshi): Converter.Factory = MoshiConverterFactory.create(moshiConverter).asLenient()

}