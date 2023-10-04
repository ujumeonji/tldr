package run.cd80.tldr.lib.http

import run.cd80.tldr.lib.http.dto.HttpRequestScopeBuilder
import run.cd80.tldr.lib.http.dto.HttpResponse

interface HttpClient {

    suspend fun get(url: String, block: HttpRequestScopeBuilder.() -> Unit): HttpResponse

    suspend fun post(url: String, block: HttpRequestScopeBuilder.() -> Unit): HttpResponse

    suspend fun put(url: String, block: HttpRequestScopeBuilder.() -> Unit): HttpResponse

    suspend fun delete(url: String, block: HttpRequestScopeBuilder.() -> Unit): HttpResponse

    suspend fun patch(url: String, block: HttpRequestScopeBuilder.() -> Unit): HttpResponse
}
