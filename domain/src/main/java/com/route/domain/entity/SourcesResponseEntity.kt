package com.route.domain.entity


data class SourcesResponseEntity(

    val sources: List<SourcesItemEntity>? = null,
    val status: String? = null,
    val message: String? = null,
    val code: String? = null,

    )

data class SourcesItemEntity(
    val name: String? = null,
    val id: String? = null,
)
