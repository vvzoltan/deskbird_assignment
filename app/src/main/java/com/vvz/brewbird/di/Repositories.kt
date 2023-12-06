package com.vvz.brewbird.di

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.vvz.brewbird.data.BreweryRepository
import com.vvz.brewbird.data.BreweryRepositoryImpl
import com.vvz.brewbird.data.local.LocalDatasource
import com.vvz.brewbird.data.local.LocalDatasourceImpl
import com.vvz.brewbird.data.remote.RemoteDatasource
import com.vvz.brewbird.data.remote.RemoteDatasourceImpl
import com.vvz.brewbird.dataStore
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
class Repositories {

    @Provides
    fun providesLocalRepo(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("brewery_preferences", Context.MODE_PRIVATE)
    }

    @Provides
    fun providesDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }

    @Provides
    fun providesRepository(localDatasource: LocalDatasource, remoteDatasource: RemoteDatasource): BreweryRepository {
        return BreweryRepositoryImpl(local = localDatasource, remote = remoteDatasource, context = Dispatchers.IO)
    }


    @Module
    @InstallIn(SingletonComponent::class)
    interface Bindings {

        @Binds
        fun bindsLocalDatasource(impl: LocalDatasourceImpl): LocalDatasource

        @Binds
        fun bindsRemoteDatasource(impl: RemoteDatasourceImpl): RemoteDatasource

    }

}