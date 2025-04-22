package com.yonggi.wikipediasearch.domain.repository

import android.graphics.Bitmap
import com.yonggi.wikipediasearch.domain.entity.MediaList
import com.yonggi.wikipediasearch.domain.entity.Summary
import java.net.URL

interface SearchRepository {

    // 요약 정보 API
    suspend fun getSummaryInfo(keyword: String): Summary

    // 미디어 리스트 검색
    suspend fun getMediaList(keyword: String): MediaList

    suspend fun getBitmap(url: URL): Bitmap?
}