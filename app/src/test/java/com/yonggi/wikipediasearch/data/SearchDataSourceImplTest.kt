package com.yonggi.wikipediasearch.data

import android.graphics.Bitmap
import com.yonggi.wikipediasearch.data.datasource.remote.SearchDataSourceImpl
import com.yonggi.wikipediasearch.data.datasource.remote.dto.MediaListDto
import com.yonggi.wikipediasearch.data.datasource.remote.dto.SummaryDto
import com.yonggi.wikipediasearch.data.network.image.ImageLoader
import com.yonggi.wikipediasearch.data.network.search.SearchService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.kotlin.whenever
import java.net.URL

@ExperimentalCoroutinesApi
class SearchDataSourceImplTest {

    private val service: SearchService = mock()
    private val loader: ImageLoader = mock()
    private lateinit var dataSource: SearchDataSourceImpl

    @Before
    fun setup() {
        dataSource = SearchDataSourceImpl(service, loader)
    }

    @Test
    fun getSummaryInfoTest() = runTest {
        val dummy = SummaryDto("타이틀", "요약", null)
        whenever(service.getSummaryInfo("고양이")).thenReturn(dummy)

        val result = dataSource.getSummaryInfo("고양이")

        assertEquals(dummy, result)
        verify(service).getSummaryInfo("고양이")
    }

    @Test
    fun getMediaListTest() = runTest {
        val dummy = MediaListDto("rev123", "tid123", emptyList())
        whenever(service.getMediaList("강아지")).thenReturn(dummy)

        val result = dataSource.getMediaList("강아지")

        assertEquals(dummy, result)
        verify(service).getMediaList("강아지")
    }

    @Test
    fun getBitmapTest() = runTest {
        val url = URL("https://test.com/img.jpg")
        val dummyBitmap: Bitmap = mock()
        whenever(loader.fetch(url)).thenReturn(dummyBitmap)

        val result = dataSource.getBitmap(url)

        assertEquals(dummyBitmap, result)
        verify(loader).fetch(url)
    }
}
