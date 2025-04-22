package com.yonggi.wikipediasearch.domain.usecase

import com.yonggi.wikipediasearch.domain.repository.SearchRepository
import com.yonggi.wikipediasearch.presentation.model.MediaListUiModel
import com.yonggi.wikipediasearch.presentation.model.toUiModel

data class RefreshParams(
    val keyword: String,
    val revision: String
)

class RefreshMediaListUseCase(private val repository: SearchRepository)
    : UseCase<RefreshParams,MediaListUiModel>() {

    override suspend fun execute(params: RefreshParams): MediaListUiModel {
        val mediaList = repository.getMediaList(keyword = params.keyword)
        return mediaList.toUiModel(revision = params.revision)
    }

}