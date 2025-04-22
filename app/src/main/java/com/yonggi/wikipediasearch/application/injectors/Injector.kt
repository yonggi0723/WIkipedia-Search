package com.yonggi.wikipediasearch.application.injectors

import android.content.Context
import com.yonggi.wikipediasearch.data.datasource.remote.SearchDataSourceImpl
import com.yonggi.wikipediasearch.data.network.image.ImageLoader
import com.yonggi.wikipediasearch.data.network.image.ImageLoaderImpl
import com.yonggi.wikipediasearch.data.network.search.SearchServiceImpl
import com.yonggi.wikipediasearch.data.respository.SearchRepositoryImpl
import com.yonggi.wikipediasearch.domain.usecase.GetBitmapUseCase
import com.yonggi.wikipediasearch.domain.usecase.RefreshMediaListUseCase
import com.yonggi.wikipediasearch.domain.usecase.SearchUseCase

object Injector {

    private lateinit var loader: ImageLoader

    val service by lazy { SearchServiceImpl() }

    val dataSource by lazy { SearchDataSourceImpl(service, loader) }

    val repository by lazy { SearchRepositoryImpl(dataSource) }

    fun init(context: Context) {
        loader = ImageLoaderImpl(context)
    }

    fun setImageLoader(custom: ImageLoader) {
        loader = custom
    }

    fun provideSearchUseCase(): SearchUseCase = SearchUseCase(repository)

    fun provideRefreshUseCase(): RefreshMediaListUseCase = RefreshMediaListUseCase(repository)

    fun provideBitmapUseCase(): GetBitmapUseCase = GetBitmapUseCase(repository)

}