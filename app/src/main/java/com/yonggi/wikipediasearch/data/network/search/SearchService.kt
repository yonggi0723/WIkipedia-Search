package com.yonggi.wikipediasearch.data.network.search

import com.yonggi.wikipediasearch.data.datasource.remote.dto.MediaListDto
import com.yonggi.wikipediasearch.data.datasource.remote.dto.SummaryDto

interface SearchService {

    // 요약 정보 API
    suspend fun getSummaryInfo(keyword: String): SummaryDto

    // 미디어 리스트 검색
    suspend fun getMediaList(keyword: String): MediaListDto

}