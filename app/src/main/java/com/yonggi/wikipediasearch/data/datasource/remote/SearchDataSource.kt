package com.yonggi.wikipediasearch.data.datasource.remote

import android.graphics.Bitmap
import com.yonggi.wikipediasearch.data.datasource.remote.dto.MediaListDto
import com.yonggi.wikipediasearch.data.datasource.remote.dto.SummaryDto
import java.net.URL

interface SearchDataSource {

    // 요약 정보 API
    suspend fun getSummaryInfo(keyword: String): SummaryDto

    // 미디어 리스트 검색
    suspend fun getMediaList(keyword: String): MediaListDto

    // 이미지 불러오기
    suspend fun getBitmap(url: URL): Bitmap?
}