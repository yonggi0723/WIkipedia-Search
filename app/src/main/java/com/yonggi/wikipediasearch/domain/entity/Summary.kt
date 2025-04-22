package com.yonggi.wikipediasearch.domain.entity

data class Summary(
    val title: String,
    val extract: String,
    val imgUrl: String,
    val width : Int,
    val height: Int
)