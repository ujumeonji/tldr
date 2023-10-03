package run.cd80.tldr.core.http.impl

import com.google.gson.Gson
import fuel.httpMethod
import run.cd80.tldr.core.http.HttpClient
import run.cd80.tldr.core.http.dto.HttpRequestScopeBuilder
import run.cd80.tldr.core.http.dto.HttpResponse

class FuelHttpClient : HttpClient {

    override suspend fun get(url: String, block: HttpRequestScopeBuilder.() -> Unit): HttpResponse =
        request("GET", url, block)

    override suspend fun post(url: String, block: HttpRequestScopeBuilder.() -> Unit): HttpResponse =
        request("POST", url, block)

    override suspend fun put(url: String, block: HttpRequestScopeBuilder.() -> Unit): HttpResponse =
        request("PUT", url, block)

    override suspend fun delete(url: String, block: HttpRequestScopeBuilder.() -> Unit): HttpResponse =
        request("DELETE", url, block)

    override suspend fun patch(url: String, block: HttpRequestScopeBuilder.() -> Unit): HttpResponse =
        request("PATCH", url, block)

    private suspend fun request(
        method: String,
        url: String,
        block: HttpRequestScopeBuilder.() -> Unit
    ): HttpResponse {
        val request = HttpRequestScopeBuilder().apply(block).build()

        return url
            .httpMethod(
                method = method,
                parameters = request.parameters,
                headers = request.headers.toMap(),
                body = Gson().toJson(request.body),
            ).let {
                HttpResponse(it.statusCode, it.body)
            }
    }
}
