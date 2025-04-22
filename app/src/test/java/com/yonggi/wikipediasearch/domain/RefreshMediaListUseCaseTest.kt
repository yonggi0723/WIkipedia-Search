package com.yonggi.wikipediasearch.domain

import com.yonggi.wikipediasearch.domain.entity.Media
import com.yonggi.wikipediasearch.domain.entity.MediaList
import com.yonggi.wikipediasearch.domain.repository.SearchRepository
import com.yonggi.wikipediasearch.domain.usecase.RefreshMediaListUseCase
import com.yonggi.wikipediasearch.domain.usecase.RefreshParams
import com.yonggi.wikipediasearch.presentation.model.toUiModel
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class RefreshMediaListUseCaseTest {

    private val repository = mock(SearchRepository::class.java)
    private lateinit var useCase: RefreshMediaListUseCase

    @Before
    fun setup() {
        useCase = RefreshMediaListUseCase(repository)
    }

    @Test
    fun executeTest() = runBlocking {
        val params = RefreshParams(keyword = "고양이", revision = "rev123")
        val media = Media(title = "타이틀", caption = "설명", imgUrl = "url")
        val mediaList = MediaList(items = listOf(media), revision = "rev456")
        val expected = mediaList.toUiModel(revision = "rev123")

        `when`(repository.getMediaList("고양이")).thenReturn(mediaList)
        val result = useCase.execute(params)
        assertEquals(expected, result)
    }

    @Test
    fun needToUpdateTest() {
        val revision = "rev-123"
        val mediaList = MediaList(revision = revision, items = emptyList())

        val result = mediaList.needsUpdate(current = "rev-123")

        assertFalse(result) // <- 이 케이스가 필요!
    }
}
