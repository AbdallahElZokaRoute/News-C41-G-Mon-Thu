package com.route.data.mappers

import com.route.data.api.model.SourcesItem
import com.route.data.api.model.SourcesResponse
import com.route.domain.entity.SourcesItemEntity
import com.route.domain.entity.SourcesResponseEntity

fun SourcesResponse.toEntity(): SourcesResponseEntity {
    return SourcesResponseEntity(sources?.map { it.toEntity() }, status, message, code)
}

fun SourcesItem.toEntity(): SourcesItemEntity {
    return SourcesItemEntity(name, id)
}
