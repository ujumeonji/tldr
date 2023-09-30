package run.cd80.tldr.core.http.impl

import fuel.httpMethod
import run.cd80.tldr.core.http.HttpClient
import run.cd80.tldr.core.http.HttpClientOption
import run.cd80.tldr.core.http.dto.HttpResponse
import kotlin.collections.set

class FuelHttpClient : HttpClient, HttpClientOption {

    private lateinit var url: String

    private lateinit var method: HttpMethod

    private val queryParam = mutableMapOf<String, String>()

    private val header = mutableMapOf<String, String>()

    private val body = mutableMapOf<String, String>()

    override fun get(url: String): HttpClientOption =
        apply {
            this.url = url
            this.method = HttpMethod.GET
        }

    override fun post(url: String): HttpClientOption =
        apply {
            this.url = url
            this.method = HttpMethod.POST
        }

    override fun put(url: String): HttpClientOption =
        apply {
            this.url = url
            this.method = HttpMethod.PUT
        }

    override fun delete(url: String): HttpClientOption =
        apply {
            this.url = url
            this.method = HttpMethod.DELETE
        }

    override fun patch(url: String): HttpClientOption =
        apply {
            this.url = url
            this.method = HttpMethod.PATCH
        }

    override fun queryParam(key: String, value: String): HttpClientOption =
        apply { queryParam[key] = value }

    override fun header(key: String, value: String): HttpClientOption =
        apply { header[key] = value }

    override fun body(body: String): HttpClientOption =
        apply { this.body["body"] = body }

    override suspend fun execute() =
        url.httpMethod(
            parameters = queryParam.toList(),
            method = method.toString(),
            body = body.toString(),
            headers = header
        ).let {
            HttpResponse(
                statusCode = it.statusCode,
                body = it.body,
            )
        }

    private enum class HttpMethod {
        GET, POST, PUT, DELETE, PATCH
    }
}
