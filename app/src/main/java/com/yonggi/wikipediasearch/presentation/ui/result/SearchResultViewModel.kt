package com.yonggi.wikipediasearch.presentation.ui.result

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yonggi.wikipediasearch.R
import com.yonggi.wikipediasearch.application.utils.SingleLiveEvent
import com.yonggi.wikipediasearch.data.network.client.HttpException
import com.yonggi.wikipediasearch.data.network.client.NetworkUnavailableException
import com.yonggi.wikipediasearch.data.network.client.ParsingException
import com.yonggi.wikipediasearch.domain.entity.MediaList
import com.yonggi.wikipediasearch.domain.entity.Summary
import com.yonggi.wikipediasearch.domain.usecase.GetBitmapUseCase
import com.yonggi.wikipediasearch.domain.usecase.RefreshMediaListUseCase
import com.yonggi.wikipediasearch.domain.usecase.RefreshParams
import com.yonggi.wikipediasearch.domain.usecase.SearchUseCase
import com.yonggi.wikipediasearch.presentation.event.UiEffect
import com.yonggi.wikipediasearch.presentation.state.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL

class SearchResultViewModelFactory(
    private val keyword: String,
    private val searchUseCase: SearchUseCase,
    private val refreshUseCase: RefreshMediaListUseCase,
    private val getBitmapUseCase: GetBitmapUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchResultViewModel::class.java)) {
            return SearchResultViewModel(keyword, searchUseCase, refreshUseCase, getBitmapUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class SearchResultViewModel(
    private val keyword: String,
    private val search: SearchUseCase,
    private val refresh: RefreshMediaListUseCase,
    private val bitmap: GetBitmapUseCase
): ViewModel() {

    private val _summary = MutableLiveData<Summary>()
    val summary: LiveData<Summary> get() = _summary

    private val _mediaList = MutableLiveData<MediaList>()
    val mediaList: LiveData<MediaList> get() = _mediaList

    private val _sum_img = MutableLiveData<Bitmap>()
    val sum_img: LiveData<Bitmap> get() = _sum_img

    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> get() = _uiState

    private val _uiEffect = SingleLiveEvent<UiEffect>()
    val uiEffect: LiveData<UiEffect> get() = _uiEffect


    init {
        fetchResult()
    }

    private fun fetchResult() {
        _uiState.postValue(UiState.Loading)

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = search.execute(keyword)

                if (result.list.items.isEmpty()) {
                    _uiState.postValue(UiState.Empty(R.string.no_search_msg))
                } else {
                    _summary.postValue(result.summary)
                    _mediaList.postValue(result.list)
                    _uiState.postValue(UiState.Success(result.summary, result.list))
                }

            } catch (e: Throwable) {
                handleError(e) {
                    _uiState.postValue(UiState.Empty(R.string.no_search_msg))
                }
            }

        }
    }

    fun swipeRefresh() {
        _uiState.postValue(UiState.Loading)

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val request = RefreshParams(
                    revision = mediaList.value?.revision.orEmpty(),
                    keyword = keyword
                )
                val result = refresh.execute(request)

                if (result.items.items.isEmpty()) {
                    _uiState.postValue(UiState.Empty(R.string.no_update_msg))
                } else {
                    _uiState.postValue(UiState.Success(summary.value!!, result.items))
                }
            } catch (e: Throwable) {
                handleError(e)
            }
        }
    }


    fun getBitmap(url: String) {
        Log.e("TEST","URL : $url")

        if (url.isEmpty()) return
        viewModelScope.launch(Dispatchers.IO) {
            bitmap.execute(URL(url))?.let { bmp ->
                _sum_img.postValue(bmp)
            }
        }
    }

    private fun handleError(e: Throwable, onEmpty: (() -> Unit)? = null) {
        when (e) {
            is HttpException -> {
                if (e.code == 404 && onEmpty != null) {
                    onEmpty()
                } else {
                    _uiEffect.postValue(UiEffect.ShowErrorMessage(R.string.server_error))
                }
            }
            is NetworkUnavailableException -> {
                _uiEffect.postValue(UiEffect.ShowErrorMessage(R.string.network_error))
            }
            is ParsingException -> {
                _uiEffect.postValue(UiEffect.ShowErrorMessage(R.string.parsing_error))
            }
            else -> {
                _uiEffect.postValue(UiEffect.ShowErrorMessage(R.string.unknown_error))
            }
        }
    }
}