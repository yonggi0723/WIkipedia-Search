package com.yonggi.wikipediasearch.data.network.client

import com.yonggi.customhttp.ConnectionFactoryImpl
import com.yonggi.customhttp.HttpClient
import com.yonggi.customhttp.HttpCustomBuilder

object Network {

    private val resource: HttpCustomBuilder

    private val resource_server = "https://en.wikipedia.org/api/rest_v1/page"

    init {
        resource = getBuilder( baseUrl = resource_server)
    }

    fun get(path: String): String {
        return resource.get(path)
    }

    fun post(path: String, body: Any): String {
        return resource.post(path, body)
    }

    fun put(path: String, body: Any): String {
        return resource.put(path, body)
    }

    fun delete(path: String): String {
        return resource.delete(path)
    }

    private fun getBuilder(baseUrl: String, headers: Map<String, String> = emptyMap()) : HttpCustomBuilder {
        return HttpCustomBuilder.builder()
            .baseurl(baseUrl)
            .client(getClient(headers))
            .connectionFactory(ConnectionFactoryImpl())
            .build()
    }

    private fun getClient(headers: Map<String, String> = emptyMap()): HttpClient {
        return HttpClient(
            headers = headers,
            connectTimeOut = 15000,
            readTimeOut =  15000,
            writeTimeOut = 15000
        )
    }

}