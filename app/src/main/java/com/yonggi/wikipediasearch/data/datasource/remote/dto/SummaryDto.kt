package com.yonggi.wikipediasearch.data.datasource.remote.dto

import com.yonggi.wikipediasearch.domain.entity.Summary

data class SummaryDto(
    val title: String,
    val extract: String,
    val thumbnail: Thumbnail?
)

data class Thumbnail(
    val source: String?,
    val width: Int?,
    val height: Int?
)

fun SummaryDto.toDomain(): Summary {
    return Summary(
        title = title,
        extract = extract,
        imgUrl = thumbnail?.source ?: "",
        width = thumbnail?.width ?: 0,
        height = thumbnail?.width ?: 0
    )
}