package com.yonggi.wikipediasearch.data.network.image

import android.graphics.Bitmap
import java.net.URL

interface ImageLoader {

    suspend fun fetch(url: URL): Bitmap?
}