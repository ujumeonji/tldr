package run.cd80.tldr.lib.http.impl

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.jackson.jackson
import run.cd80.tldr.lib.http.HttpClient
import run.cd80.tldr.lib.http.dto.HttpRequestScopeBuilder
import run.cd80.tldr.lib.http.dto.HttpResponse
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import io.ktor.client.HttpClient as KtorHttpClient
import io.ktor.http.HttpMethod as KtorHttpMethod

class KtorHttpClient : HttpClient {
    private val client = KtorHttpClient(CIO) {
        install(ContentNegotiation) {
            jackson {
                enable(SerializationFeature.INDENT_OUTPUT)
                disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                registerModule(
                    JavaTimeModule().apply {
                        addDeserializer(
                            LocalDateTime::class.java,
                            LocalDateTimeDeserializer(DATE_TIME_FORMATTER),
                        )
                    },
                )
            }
        }
        install(HttpTimeout) {
            requestTimeoutMillis = TIMEOUT
        }
    }

    override suspend fun get(url: String, block: HttpRequestScopeBuilder.() -> Unit): HttpResponse =
        request(KtorHttpMethod.Get, url, block)

    override suspend fun post(url: String, block: HttpRequestScopeBuilder.() -> Unit): HttpResponse =
        request(KtorHttpMethod.Post, url, block)

    override suspend fun put(url: String, block: HttpRequestScopeBuilder.() -> Unit): HttpResponse =
        request(KtorHttpMethod.Put, url, block)

    override suspend fun delete(url: String, block: HttpRequestScopeBuilder.() -> Unit): HttpResponse =
        request(KtorHttpMethod.Delete, url, block)

    override suspend fun patch(url: String, block: HttpRequestScopeBuilder.() -> Unit): HttpResponse =
        request(KtorHttpMethod.Patch, url, block)

    private suspend fun request(
        method: KtorHttpMethod,
        url: String,
        block: HttpRequestScopeBuilder.() -> Unit,
    ): HttpResponse {
        val request = HttpRequestScopeBuilder().apply(block).build()

        val response = client.request {
            this.method = method
            url(request.uri ?: url)
            contentType(ContentType.Application.Json)
            request.parameters.forEach { (key, value) -> parameter(key, value) }
            request.headers.forEach { (key, value) -> header(key, value) }
            setBody(request.body)
        }

        return HttpResponse(response.status.value, response.body())
    }

    companion object {

        private const val TIMEOUT: Long = 5000

        private val DATE_TIME_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    }
}
