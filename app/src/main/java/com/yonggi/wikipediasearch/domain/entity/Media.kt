package com.yonggi.wikipediasearch.domain.entity


data class MediaList(
    val revision: String,
    val items: List<Media> = emptyList()) {
    fun needsUpdate(current: String): Boolean = this.revision != current
}

data class Media(
    val title: String,
    val caption: String,
    val imgUrl: String
)
