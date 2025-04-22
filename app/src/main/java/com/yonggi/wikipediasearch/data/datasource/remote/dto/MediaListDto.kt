package com.yonggi.wikipediasearch.data.datasource.remote.dto

import com.yonggi.wikipediasearch.domain.entity.Media
import com.yonggi.wikipediasearch.domain.entity.MediaList

/**
 * @TODO
 * 리비전 정보가 있음, UI 갱신시
 * 갱신 여부를 이걸로 판단하라는 의미로 해석됨
 *
 * 주의 API 분석 중,
 * srcset 또는 caption 필드가 없는 경우 발견
 * App crash 확률이 존재, 반드시 관련 처리가 필요
 * 일단 Nullable 처리를 추가
 */
data class MediaListDto(
    val revision: String,
    val tid: String,
    val items: List<MediaItemDto>?
)

data class MediaItemDto(
    val title: String,
    val caption: Caption?,
    val srcset : List<SourceSet>?
)

data class Caption(
    val text: String?
)

data class SourceSet(
    val src : String?,
    val scale: String
)

/**
 * scale 에 따라 배열 형식으로 데이터를 반환
 * 어느것을 우선할지 조건이 없음
 * 1x 를 선호 이미지로 판정
 * 그 후, 순서에 따라 마지막 값을 이미지 스케일로 판정
 */
fun MediaListDto.toDomain():MediaList {
    return MediaList(
        revision = this.revision,
        items = items?.map { item->
            Media(
                title = item.title,
                caption = item.caption?.text ?: "",
                imgUrl  = item.srcset
                    ?.find { it.scale == "1x" }
                    ?.src ?: item.srcset?.lastOrNull()?.src ?:""
            )
        } ?: emptyList()
    )
}