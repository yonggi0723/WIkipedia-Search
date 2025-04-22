package com.yonggi.wikipediasearch.presentation.model

import com.yonggi.wikipediasearch.domain.entity.MediaList

data class MediaListUiModel(
    val items: MediaList,
    val isUpdated: Boolean
)

fun MediaList.toUiModel(revision: String): MediaListUiModel {
    return MediaListUiModel(
        items = this,
        isUpdated = this.needsUpdate(current = revision)
    )
}