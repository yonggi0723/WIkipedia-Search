package com.yonggi.wikipediasearch.data.network.client

import java.io.IOException

class ParsingException(message: String, cause: Throwable) : RuntimeException(message, cause)

class NetworkUnavailableException(message: String, cause: Throwable) : IOException(message, cause)

class HttpException(val code: Int, message: String) : IOException(message)

suspend fun safeGet(path: String): String {
    return try {
        Network.get(path)
    } catch (e: HttpException) {
        throw e
    } catch (e: IOException) {
        throw NetworkUnavailableException("네트워크 연결 실패", e)
    }
}

fun <T> safeParse(block: () -> T): T {
    return try {
        block()
    } catch (e: Exception) {
        throw ParsingException("JSON 파싱 실패", e)
    }
}