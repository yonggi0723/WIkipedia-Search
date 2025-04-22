package com.yonggi.wikipediasearch.domain.usecase

import android.graphics.Bitmap
import com.yonggi.wikipediasearch.domain.repository.SearchRepository
import java.net.URL

class GetBitmapUseCase(private val repository: SearchRepository): UseCase<URL, Bitmap?>() {

    override suspend fun execute(params: URL): Bitmap? {
        return repository.getBitmap(url = params)
    }

}