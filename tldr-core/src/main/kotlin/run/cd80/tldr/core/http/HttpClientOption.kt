package run.cd80.tldr.core.http

import run.cd80.tldr.core.http.dto.HttpResponse

interface HttpClientOption {

    fun queryParam(key: String, value: String): HttpClientOption

    fun header(key: String, value: String): HttpClientOption

    fun body(jsonBody: Map<String, Any>): HttpClientOption

    suspend fun execute(): HttpResponse
}
