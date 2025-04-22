package com.yonggi.wikipediasearch.application

import com.yonggi.wikipediasearch.application.utils.Utils
import org.junit.Assert.assertEquals
import kotlin.test.Test

class ApplicationUtilsTest {

    @Test
    fun UtilsExtractTest() {
        val input = "  안녕   세상   코틀린  자바 "
        val result = Utils.extract(input)
        assertEquals("안녕 세상 코틀린", result)
    }

}