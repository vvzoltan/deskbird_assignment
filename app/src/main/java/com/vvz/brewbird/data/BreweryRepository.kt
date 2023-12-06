package com.vvz.brewbird.data

import com.vvz.brewbird.data.local.LocalDatasource
import com.vvz.brewbird.data.remote.RemoteDatasource
import com.vvz.brewbird.domain.models.BreweryDetails
import com.vvz.brewbird.domain.models.BreweryPreview
import com.vvz.brewbird.domain.models.BreweryType
import com.vvz.brewbird.domain.models.PagingMeta
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


interface BreweryRepository {
    suspend fun getBreweries(page: Int, resultsPerPage: Int = 10, type: BreweryType? = null): Pair<List<BreweryPreview>, PagingMeta>
    suspend fun getBreweryDetails(id: String): BreweryDetails
    fun getFavouriteIds(): Flow<Set<String>>
    suspend fun addFavourite(id: String)
    suspend fun removeFavourite(id: String)
    suspend fun loadFavourites(ids: Set<String>): List<BreweryPreview>
}


class BreweryRepositoryImpl @Inject constructor(private val local: LocalDatasource,
                                                private var remote: RemoteDatasource,
                                                private val context: CoroutineContext = Dispatchers.IO) : BreweryRepository {

    override suspend fun getBreweries(page: Int,
                                      resultsPerPage: Int,
                                      type: BreweryType?): Pair<List<BreweryPreview>, PagingMeta> = withContext(context) {

        remote.getBreweries(page, resultsPerPage, type)
    }

    override suspend fun getBreweryDetails(id: String): BreweryDetails = withContext(context) {
        remote.getBreweryDetails(id = id)
    }

    override fun getFavouriteIds(): Flow<Set<String>> {
        return local.currentFavourites()
    }


    override suspend fun addFavourite(id: String) {
        local.addFavourite(id)
    }


    override suspend fun removeFavourite(id: String) {
        local.removeFavourite(id)
    }


    override suspend fun loadFavourites(ids: Set<String>): List<BreweryPreview> = withContext(context) {
        remote.getFavourites(ids = ids)
    }
}