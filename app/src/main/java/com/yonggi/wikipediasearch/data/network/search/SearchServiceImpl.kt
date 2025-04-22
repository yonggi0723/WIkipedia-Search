package com.yonggi.wikipediasearch.data.network.search

import com.yonggi.wikipediasearch.data.datasource.remote.dto.MediaListDto
import com.yonggi.wikipediasearch.data.datasource.remote.dto.SummaryDto
import com.yonggi.wikipediasearch.data.datasource.remote.mapper.MediaMapper
import com.yonggi.wikipediasearch.data.datasource.remote.mapper.SummaryMapper
import com.yonggi.wikipediasearch.data.network.client.safeGet
import com.yonggi.wikipediasearch.data.network.client.safeParse

class SearchServiceImpl: SearchService {

    override suspend fun getSummaryInfo(keyword: String): SummaryDto {
        val json = safeGet("/summary/${keyword}")
        return safeParse { SummaryMapper.mapToSummaryDto(json) }
    }

    override suspend fun getMediaList(keyword: String): MediaListDto {
        val json = safeGet("/media-list/${keyword}")
        return safeParse {  MediaMapper.mapToMediaListDto(json) }
    }

}