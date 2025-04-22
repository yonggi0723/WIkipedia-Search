package com.yonggi.wikipediasearch.presentation.state

import com.yonggi.wikipediasearch.domain.entity.MediaList
import com.yonggi.wikipediasearch.domain.entity.Summary

sealed class UiState {
    object Loading : UiState()
    data class Success(val summary: Summary, val list: MediaList) : UiState()
    data class Empty(val resId: Int) : UiState()
    data class Error(val resId: Int) : UiState()
}