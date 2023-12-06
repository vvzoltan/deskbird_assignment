package com.vvz.brewbird.data.remote.dto

import com.squareup.moshi.Json
import com.vvz.brewbird.domain.models.BreweryPreview
import com.vvz.brewbird.domain.models.BreweryType


data class BreweryPreviewDto(
    @Json(name = "id") val id: String,
    @Json(name = "name") val name: String,
    @Json(name = "city") val city: String,
    @Json(name = "country") val country: String,
    @Json(name = "brewery_type") val type: String
)


fun BreweryPreviewDto.toDomainModel(): BreweryPreview {
    return BreweryPreview(id = id,
                          name = name,
                          city = city,
                          country = country,
                          type = BreweryType.fromName(type))
}