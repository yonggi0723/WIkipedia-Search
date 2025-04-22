package com.yonggi.wikipediasearch.domain

import android.graphics.Bitmap
import com.yonggi.wikipediasearch.domain.repository.SearchRepository
import com.yonggi.wikipediasearch.domain.usecase.GetBitmapUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertNull
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import java.net.URL

class GetBitmapUseCaseTest {

    private val repository = mock(SearchRepository::class.java)

    private lateinit var useCase: GetBitmapUseCase

    @Before
    fun setup() {
        useCase = GetBitmapUseCase(repository)
    }

    @Test
    fun executeTest() = runBlocking {
        val url = URL("https://example.com/image.png")
        val expectedBitmap = mock(Bitmap::class.java)
        `when`(repository.getBitmap(url)).thenReturn(expectedBitmap)

        val result1 = useCase.execute(url)
        assertEquals(expectedBitmap, result1)

        `when`(repository.getBitmap(url)).thenReturn(null)
        val result2 = useCase.execute(url)
        assertNull(result2)
    }

}
