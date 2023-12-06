package com.vvz.brewbird.domain.models

data class Address(
    val street: String?,
    val address1: String?,
    val address2: String?,
    val address3: String?,
    val state: String,
    val postCode: String,
    val city: String,
    val country: String,
    val longitude: Double?,
    val latitude: Double?,
)
