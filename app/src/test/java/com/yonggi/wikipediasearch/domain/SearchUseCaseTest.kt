package com.yonggi.wikipediasearch.domain

import com.yonggi.wikipediasearch.domain.entity.Summary
import com.yonggi.wikipediasearch.domain.entity.MediaList
import com.yonggi.wikipediasearch.domain.repository.SearchRepository
import com.yonggi.wikipediasearch.domain.usecase.SearchUseCase
import com.yonggi.wikipediasearch.presentation.model.SearchUiModel

import org.junit.Test
import org.junit.Before
import org.junit.Assert.assertEquals
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import kotlinx.coroutines.runBlocking


class SearchUseCaseTest {

    private val repository = mock(SearchRepository::class.java)
    private lateinit var useCase: SearchUseCase

    @Before
    fun setup() {
        useCase = SearchUseCase(repository)
    }

    @Test
    fun executeTest() = runBlocking {
        // given
        val keyword = "고양이"
        val summary = Summary("설명", "url", "",100,200)
        val mediaList = MediaList(items = emptyList(), revision = "rev1")
        val expected = SearchUiModel(summary, mediaList)

        `when`(repository.getSummaryInfo(keyword)).thenReturn(summary)
        `when`(repository.getMediaList(keyword)).thenReturn(mediaList)

        val result = useCase.execute(keyword)

        assertEquals(expected, result)
    }

    @Test
    fun executeKeywordNullTest() = runBlocking {
        val keyword = " " // 공백 문자열
        val summary = Summary("기본", "url", "",100, 200)
        val mediaList = MediaList(items = listOf(), revision = "rev")

        `when`(repository.getSummaryInfo(keyword)).thenReturn(summary)
        `when`(repository.getMediaList(keyword)).thenReturn(mediaList)

        val result = useCase.execute(keyword)

        assertEquals(summary, result.summary)
        assertEquals(mediaList, result.list)
    }
}
