package com.vvz.brewbird.domain.models

data class BreweryPreview(
    val id: String,
    val name: String,
    val city: String,
    val country: String,
    val type: BreweryType
)
