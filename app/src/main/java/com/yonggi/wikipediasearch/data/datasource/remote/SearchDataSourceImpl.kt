package com.yonggi.wikipediasearch.data.datasource.remote

import android.graphics.Bitmap
import com.yonggi.wikipediasearch.data.datasource.remote.dto.MediaListDto
import com.yonggi.wikipediasearch.data.datasource.remote.dto.SummaryDto
import com.yonggi.wikipediasearch.data.network.image.ImageLoader
import com.yonggi.wikipediasearch.data.network.search.SearchService
import java.net.URL

class SearchDataSourceImpl (
    private val service: SearchService,
    private val loader: ImageLoader
): SearchDataSource {

    override suspend fun getSummaryInfo(keyword: String): SummaryDto {
        return service.getSummaryInfo(keyword)
    }

    override suspend fun getMediaList(keyword: String): MediaListDto {
        return service.getMediaList(keyword)
    }

    override suspend fun getBitmap(url: URL): Bitmap? {
        return loader.fetch(url)
    }

}