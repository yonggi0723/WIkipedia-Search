package com.yonggi.wikipediasearch.data

import com.yonggi.wikipediasearch.data.datasource.remote.dto.SummaryDto
import com.yonggi.wikipediasearch.data.datasource.remote.dto.MediaListDto
import com.yonggi.wikipediasearch.data.datasource.remote.mapper.MediaMapper
import com.yonggi.wikipediasearch.data.datasource.remote.mapper.SummaryMapper
import com.yonggi.wikipediasearch.data.network.client.HttpException
import com.yonggi.wikipediasearch.data.network.client.Network
import com.yonggi.wikipediasearch.data.network.client.NetworkUnavailableException
import com.yonggi.wikipediasearch.data.network.client.ParsingException
import com.yonggi.wikipediasearch.data.network.search.SearchServiceImpl
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import kotlin.test.Test
import io.mockk.mockkObject
import io.mockk.every
import io.mockk.*
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import java.io.IOException

class SearchServiceTest {
    private lateinit var service: SearchServiceImpl

    @Before
    fun setup() {
        mockkObject(Network)
        mockkObject(MediaMapper)
        mockkObject(SummaryMapper)
        service = SearchServiceImpl()
    }

    @After
    fun teardown() {
        unmockkAll()
    }

    @Test
    fun getSummaryInfoSuccessTest() = runBlocking {
        val json = """{"title":"타이틀","extract":"요약","thumbnail":null}"""
        val expected = SummaryDto("타이틀", "요약", null)

        every { Network.get("/summary/고양이") } returns json
        every { SummaryMapper.mapToSummaryDto(json) } returns expected

        val result = service.getSummaryInfo("고양이")

        assertEquals("타이틀", result.title)
    }

    @Test(expected = NetworkUnavailableException::class)
    fun getSummaryInfoNetworkErrorTest(): Unit = runBlocking {
        every { Network.get(any()) } throws IOException("No network")
        service.getSummaryInfo("고양이")
    }

    @Test(expected = ParsingException::class)
    fun getSummaryInfoExceptionTest(): Unit = runBlocking {
        val json = """{ invalid }"""
        every { Network.get(any()) } returns json
        every { SummaryMapper.mapToSummaryDto(json) } throws RuntimeException("파싱 실패")
        service.getSummaryInfo("고양이")
    }

    @Test(expected = HttpException::class)
    fun getSummaryInfoHttpExceptionTest(): Unit = runBlocking {
        // given
        val keyword = "고양이"
        val httpException = HttpException(404, "Not Found")

        mockkObject(Network)  // Network.get() mocking
        coEvery { Network.get("/summary/$keyword") } throws httpException

        val service = SearchServiceImpl()

        // when
        service.getSummaryInfo(keyword) // 여기서 HttpException 발생해야 성공
    }

    @Test
    fun getMediaListSuccessTest() = runTest {
        val json = """{"revision":"rev123", "tid":"tid", "items":[]}"""
        val expected = MediaListDto("rev123", "tid", emptyList())

        every { Network.get("/media-list/고양이") } returns json
        every { MediaMapper.mapToMediaListDto(json) } returns expected

        val result = service.getMediaList("고양이")

        assertEquals("rev123", result.revision)
    }

    @Test(expected = NetworkUnavailableException::class)
    fun getMediaListNetworkErrorTest() = runTest {
        every { Network.get(any()) } throws IOException("fail")
        service.getMediaList("고양이")
    }

    @Test(expected = ParsingException::class)
    fun getMediaListExceptionTEst() = runTest {
        val json = """{ invalid json }"""
        every { Network.get(any()) } returns json
        every { MediaMapper.mapToMediaListDto(any()) } throws RuntimeException("파싱 오류")
        service.getMediaList("고양이")
    }
}