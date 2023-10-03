package run.cd80.tldr.core.http

import run.cd80.tldr.core.http.dto.HttpRequestScopeBuilder
import run.cd80.tldr.core.http.dto.HttpResponse

interface HttpClient {

    fun get(url: String, block: HttpRequestScopeBuilder.() -> Unit): HttpResponse

    fun post(url: String, block: HttpRequestScopeBuilder.() -> Unit): HttpResponse

    fun put(url: String, block: HttpRequestScopeBuilder.() -> Unit): HttpResponse

    fun delete(url: String, block: HttpRequestScopeBuilder.() -> Unit): HttpResponse

    fun patch(url: String, block: HttpRequestScopeBuilder.() -> Unit): HttpResponse
}
