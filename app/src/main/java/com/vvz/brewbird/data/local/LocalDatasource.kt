package com.vvz.brewbird.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


interface LocalDatasource {
    suspend fun addFavourite(id: String)
    suspend fun removeFavourite(id: String)
    fun currentFavourites(): Flow<Set<String>>
}


class LocalDatasourceImpl @Inject constructor(private val dataStore: DataStore<Preferences>) : LocalDatasource {

    override suspend fun addFavourite(id: String) {
        dataStore.edit { preferences ->
            val current = preferences[favouritesKey]?.toMutableSet() ?: mutableSetOf()
            preferences[favouritesKey] = current.apply { add(id) }
        }
    }

    override suspend fun removeFavourite(id: String) {
        dataStore.edit { preferences ->
            val current = preferences[favouritesKey]?.toMutableSet() ?: mutableSetOf()
            preferences[favouritesKey] = current.apply { remove(id) }
        }
    }

    override fun currentFavourites(): Flow<Set<String>> {
        return dataStore.data.map { it[favouritesKey] ?: setOf() }
    }

    companion object {
        private val favouritesKey = stringSetPreferencesKey("key_favourites")
    }
}