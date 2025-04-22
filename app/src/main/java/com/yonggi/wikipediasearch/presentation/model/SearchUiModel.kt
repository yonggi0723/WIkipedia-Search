package com.yonggi.wikipediasearch.presentation.model

import com.yonggi.wikipediasearch.domain.entity.MediaList
import com.yonggi.wikipediasearch.domain.entity.Summary

data class SearchUiModel(
    val summary: Summary,
    val list : MediaList
)

fun Pair<Summary, MediaList>.toUiModel(): SearchUiModel {
    return SearchUiModel(
        summary = first,
        list = second
    )
}