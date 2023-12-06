package com.vvz.brewbird.data.remote

import com.vvz.brewbird.data.remote.dto.BreweryDetailsDto
import com.vvz.brewbird.data.remote.dto.BreweryPreviewDto
import com.vvz.brewbird.data.remote.dto.ListMetaDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BreweryApi {

    @GET("breweries")
    suspend fun getBreweries(@Query("page") page: Int,
                             @Query("per_page") resultsPerPage: Int,
                             @Query("by_type") type: String?): List<BreweryPreviewDto>

    @GET("breweries/meta")
    suspend fun getBreweriesMeta(@Query("page") page: Int,
                                 @Query("per_page") resultsPerPage: Int,
                                 @Query("by_type") type: String?): ListMetaDto


    @GET("breweries")
    suspend fun getFavourites(@Query("by_ids") ids: String): List<BreweryPreviewDto>

    @GET("breweries/{id}")
    suspend fun getBreweries(@Path("id") id: String): BreweryDetailsDto

}