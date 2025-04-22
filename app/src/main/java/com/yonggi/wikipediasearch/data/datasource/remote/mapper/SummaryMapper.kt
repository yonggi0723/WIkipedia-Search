package com.yonggi.wikipediasearch.data.datasource.remote.mapper

import com.yonggi.wikipediasearch.data.datasource.remote.dto.SummaryDto
import com.yonggi.wikipediasearch.data.datasource.remote.dto.Thumbnail
import org.json.JSONObject

object SummaryMapper {
    fun mapToSummaryDto(json: String): SummaryDto {
        val jsonObject = JSONObject(json)

        val title = jsonObject.optString("title")
        val extract = jsonObject.optString("extract")

        val thumbnailObj = jsonObject.optJSONObject("thumbnail")
        val thumbnail = if (thumbnailObj != null) {
            Thumbnail(
                source = thumbnailObj.optString("source"),
                width = thumbnailObj.optInt("width"),
                height = thumbnailObj.optInt("height")
            )
        } else null

        return SummaryDto(title, extract, thumbnail)
    }
}