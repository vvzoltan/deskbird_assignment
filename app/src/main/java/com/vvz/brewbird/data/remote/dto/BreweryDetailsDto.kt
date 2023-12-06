package com.vvz.brewbird.data.remote.dto

import com.squareup.moshi.Json
import com.vvz.brewbird.domain.models.Address
import com.vvz.brewbird.domain.models.BreweryDetails
import com.vvz.brewbird.domain.models.BreweryType

data class BreweryDetailsDto(

    @Json(name = "id") val id: String,
    @Json(name = "name") val name: String,
    @Json(name = "street") val street: String?,
    @Json(name = "address_1") val address1: String?,
    @Json(name = "address_2") val address2: String?,
    @Json(name = "address_3") val address3: String?,
    @Json(name = "state_province") val state: String,
    @Json(name = "postal_code") val postCode: String,
    @Json(name = "city") val city: String,
    @Json(name = "country") val country: String,
    @Json(name = "longitude") val longitude: Double?,
    @Json(name = "latitude") val latitude: Double?,
    @Json(name = "brewery_type") val type: String,
    @Json(name = "phone") val phone: String?,
    @Json(name = "website_url") val website: String?

)


fun BreweryDetailsDto.toDomainModel(): BreweryDetails {
    val address = Address(street, address1, address2, address3, state, postCode, city, country, longitude, latitude)
    return BreweryDetails(id = id,
                          name = name,
                          address = address,
                          type = BreweryType.fromName(type),
                          phone = phone,
                          website = website)
}