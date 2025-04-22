package com.yonggi.wikipediasearch.data

import android.graphics.Bitmap
import com.yonggi.wikipediasearch.data.datasource.remote.SearchDataSource
import com.yonggi.wikipediasearch.data.datasource.remote.dto.Caption
import com.yonggi.wikipediasearch.data.datasource.remote.dto.MediaItemDto
import com.yonggi.wikipediasearch.data.datasource.remote.dto.MediaListDto
import com.yonggi.wikipediasearch.data.datasource.remote.dto.SourceSet
import com.yonggi.wikipediasearch.data.datasource.remote.dto.SummaryDto
import com.yonggi.wikipediasearch.data.datasource.remote.dto.Thumbnail
import com.yonggi.wikipediasearch.data.respository.SearchRepositoryImpl
import com.yonggi.wikipediasearch.domain.repository.SearchRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.mockito.Mockito.mock
import java.net.URL
import kotlin.test.Test
import org.junit.Assert.assertNotNull

class SearchRepositoryImplTest {

    private lateinit var repository: SearchRepository

    @Before
    fun setup() {
        val fakeDataSource = object : SearchDataSource {
            override suspend fun getSummaryInfo(keyword: String): SummaryDto {
                val thumbnail = Thumbnail("http://example",100,200)
                return SummaryDto("요약", "img.jpg", thumbnail)
            }

            override suspend fun getMediaList(keyword: String): MediaListDto {
                val source = SourceSet("http://example", "scale")
                val caption = Caption("test message")
                val item = MediaItemDto("test title", caption, listOf(source))
                return MediaListDto("rev123", "123", listOf(item))
            }

            override suspend fun getBitmap(url: URL): Bitmap? {
                return mock(Bitmap::class.java)
            }
        }

        repository = SearchRepositoryImpl(fakeDataSource)
    }

    @Test
    fun getSummaryInfoTest() = runBlocking {
        val result = repository.getSummaryInfo("고양이")
        assertEquals("요약", result?.title)
    }

    @Test
    fun getMediaListTest() = runBlocking {
        val result = repository.getMediaList("고양이")
        assertEquals("rev123", result?.revision)
    }

    @Test
    fun getBitmapTest() = runBlocking {
        val result = repository.getBitmap(URL("https://example.com/img.jpg"))
        assertNotNull(result)
    }

    @Test
    fun getSummaryInfoNullTest() = runBlocking {
        val dto = SummaryDto(title = "테스트",  extract= "img.jpg", null)

        val fake = object : SearchDataSource {
            override suspend fun getSummaryInfo(keyword: String): SummaryDto = dto
            override suspend fun getMediaList(keyword: String): MediaListDto = TODO()
            override suspend fun getBitmap(url: URL): Bitmap? = TODO()
        }

        val repo = SearchRepositoryImpl(fake)

        val result = repo.getSummaryInfo("고양이")
        assertEquals("테스트", result.title)
    }
}
