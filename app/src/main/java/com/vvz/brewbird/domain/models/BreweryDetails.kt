package com.vvz.brewbird.domain.models

data class BreweryDetails(
    val id: String,
    val name: String,
    val address: Address,
    val type: BreweryType,
    val phone: String?,
    val website: String?
)
