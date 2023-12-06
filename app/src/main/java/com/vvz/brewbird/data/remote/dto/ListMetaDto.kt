package com.vvz.brewbird.data.remote.dto

import com.squareup.moshi.Json
import com.vvz.brewbird.domain.models.PagingMeta

data class ListMetaDto(@Json(name = "total") val total: Int,
                       @Json(name = "page") val page: Int,
                       @Json(name = "per_page") val resultsPerPage: Int)

fun ListMetaDto.toDomainModel(): PagingMeta {
    return PagingMeta(total = total, page = page, resultsPerPage = resultsPerPage)
}