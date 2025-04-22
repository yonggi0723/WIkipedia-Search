package com.yonggi.wikipediasearch

import android.content.Context
import android.graphics.Bitmap
import android.util.LruCache
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.yonggi.wikipediasearch.data.datasource.local.DiskImageCache
import com.yonggi.wikipediasearch.data.network.image.ImageLoaderImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.net.URL

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class ImageLoaderImplTest {

    private lateinit var context: Context
    private lateinit var loader: ImageLoaderImpl
    private lateinit var diskCache: DiskImageCache

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        loader = ImageLoaderImpl(context)
        diskCache = DiskImageCache(context)
    }

    @Test
    fun memoryCacheHit() = runBlocking {
        val url = URL("https://example.com/image.png")
        val key = url.toString()
        val bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)

        // 메모리 캐시에 직접 저장
        val memCache = loader.javaClass.getDeclaredField("cache")
        memCache.isAccessible = true
        (memCache.get(loader) as LruCache<String, Bitmap>).put(key, bitmap)

        val result = loader.fetch(url)
        assertEquals(bitmap, result)
    }

    @Test
    fun diskCacheHit() = runBlocking {
        val url = URL("https://example.com/image_disk.png")
        val key = url.toString()
        val bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)

        diskCache.save(key, bitmap)

        val result = loader.fetch(url)
        assertNotNull(result)
    }

    @Test
    fun networkHit() = runBlocking {
        val url = URL("https://upload.wikimedia.org/wikipedia/commons/4/47/PNG_transparency_demonstration_1.png")
        val result = loader.fetch(url)
        assertNotNull(result)
    }
}
