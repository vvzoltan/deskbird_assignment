package com.vvz.brewbird.data.remote

import com.vvz.brewbird.data.remote.dto.toDomainModel
import com.vvz.brewbird.domain.models.BreweryDetails
import com.vvz.brewbird.domain.models.BreweryPreview
import com.vvz.brewbird.domain.models.BreweryType
import com.vvz.brewbird.domain.models.PagingMeta
import javax.inject.Inject


interface RemoteDatasource {
    suspend fun getBreweries(page: Int, resultsPerPage: Int, type: BreweryType?): Pair<List<BreweryPreview>, PagingMeta>
    suspend fun getFavourites(ids: Set<String>): List<BreweryPreview>
    suspend fun getBreweryDetails(id: String): BreweryDetails
}


class RemoteDatasourceImpl @Inject constructor(private val api: BreweryApi) : RemoteDatasource {

    override suspend fun getBreweries(page: Int,
                                      resultsPerPage: Int,
                                      type: BreweryType?): Pair<List<BreweryPreview>, PagingMeta> {

        val results = api
            .getBreweries(page = page, resultsPerPage = resultsPerPage, type = type?.name)
            .map { it.toDomainModel() }

        val meta = api
            .getBreweriesMeta(page = page, resultsPerPage = resultsPerPage, type = type?.name)
            .toDomainModel()

        return results to meta
    }


    override suspend fun getBreweryDetails(id: String): BreweryDetails {
        return api
            .getBreweries(id = id)
            .toDomainModel()
    }


    override suspend fun getFavourites(ids: Set<String>): List<BreweryPreview> {
        return api
            .getFavourites(ids = ids.joinToString(","))
            .map { it.toDomainModel() }
    }
}