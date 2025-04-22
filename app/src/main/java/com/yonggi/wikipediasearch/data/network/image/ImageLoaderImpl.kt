package com.yonggi.wikipediasearch.data.network.image

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.LruCache
import com.yonggi.wikipediasearch.data.datasource.local.DiskImageCache
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

class ImageLoaderImpl(context: Context): ImageLoader {

    private val diskCache = DiskImageCache(context)

    private val cache = object : LruCache<String, Bitmap>(
        (Runtime.getRuntime().maxMemory() / 1024 / 8).toInt()
    ) {
        override fun sizeOf(key: String, value: Bitmap): Int {
            return value.byteCount / 1024
        }
    }

    /**
     * 구조 메모리 캐싱 -> 디스크 캐싱 -> 이미지 다운로드 진행 방식
     */
    override suspend fun fetch(url: URL): Bitmap? = withContext(Dispatchers.IO) {
        val key = url.toString()
        cache.get(key)?.let { return@withContext it }

        diskCache.load(key)?.let {
            cache.put(key, it)
            return@withContext it
        }

        runCatching {
            val connection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            BitmapFactory.decodeStream(connection.inputStream)
        }.getOrNull()?.also {
            cache.put(key, it)
            diskCache.save(key, it)
        }
    }
}