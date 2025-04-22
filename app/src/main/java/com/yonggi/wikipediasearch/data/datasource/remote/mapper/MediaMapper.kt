package com.yonggi.wikipediasearch.data.datasource.remote.mapper

import com.yonggi.wikipediasearch.data.datasource.remote.dto.Caption
import com.yonggi.wikipediasearch.data.datasource.remote.dto.MediaItemDto
import com.yonggi.wikipediasearch.data.datasource.remote.dto.MediaListDto
import com.yonggi.wikipediasearch.data.datasource.remote.dto.SourceSet
import org.json.JSONObject

object MediaMapper {
    fun mapToMediaListDto(json: String): MediaListDto {
        val jsonObject = JSONObject(json)

        val revision = jsonObject.optString("revision")
        val tid = jsonObject.optString("tid")

        val itemsArray = jsonObject.optJSONArray("items")
        val items = mutableListOf<MediaItemDto>()

        for (i in 0 until (itemsArray?.length() ?: 0)) {
            val itemObj = itemsArray!!.getJSONObject(i)

            val title = itemObj.optString("title")

            val captionObj = itemObj.optJSONObject("caption")
            val caption = captionObj?.let {
                Caption(it.optString("text"))
            }

            val srcsetArray = itemObj.optJSONArray("srcset")
            val srcset = mutableListOf<SourceSet>()
            for (j in 0 until (srcsetArray?.length() ?: 0)) {
                val srcObj = srcsetArray!!.getJSONObject(j)
                srcset.add(
                    SourceSet(
                        src = srcObj.optString("src"),
                        scale = srcObj.optString("scale")
                    )
                )
            }

            items.add(MediaItemDto(title, caption, srcset))
        }

        return MediaListDto(
            revision = revision,
            tid = tid,
            items = items
        )
    }
}