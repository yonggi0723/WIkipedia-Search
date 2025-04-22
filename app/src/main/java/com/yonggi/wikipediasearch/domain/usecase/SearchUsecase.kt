package com.yonggi.wikipediasearch.domain.usecase

import com.yonggi.wikipediasearch.domain.repository.SearchRepository
import com.yonggi.wikipediasearch.presentation.model.SearchUiModel
import com.yonggi.wikipediasearch.presentation.model.toUiModel

class SearchUseCase(private val repository: SearchRepository): UseCase<String, SearchUiModel>() {

    override suspend fun execute(params: String): SearchUiModel {
        val summary = repository.getSummaryInfo(keyword = params)
        val mediaList = repository.getMediaList(keyword = params)
        return (summary to mediaList).toUiModel()
    }

}