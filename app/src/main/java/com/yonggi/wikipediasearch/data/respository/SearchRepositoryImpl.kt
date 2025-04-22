package com.yonggi.wikipediasearch.data.respository

import android.graphics.Bitmap
import com.yonggi.wikipediasearch.data.datasource.remote.SearchDataSource
import com.yonggi.wikipediasearch.data.datasource.remote.dto.toDomain
import com.yonggi.wikipediasearch.domain.entity.MediaList
import com.yonggi.wikipediasearch.domain.entity.Summary
import com.yonggi.wikipediasearch.domain.repository.SearchRepository
import java.net.URL

class SearchRepositoryImpl(
    private val datasource: SearchDataSource): SearchRepository {

    override suspend fun getSummaryInfo(keyword: String): Summary {
        return datasource.getSummaryInfo(keyword).toDomain()
    }

    override suspend fun getMediaList(keyword: String): MediaList {
        return datasource.getMediaList(keyword).toDomain()
    }

    override suspend fun getBitmap(url: URL): Bitmap? {
        return datasource.getBitmap(url)
    }
}