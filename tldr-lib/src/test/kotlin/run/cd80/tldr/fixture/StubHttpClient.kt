package run.cd80.tldr.fixture

import com.google.gson.Gson
import run.cd80.tldr.lib.http.HttpClient
import run.cd80.tldr.lib.http.dto.HttpRequestScopeBuilder
import run.cd80.tldr.lib.http.dto.HttpResponse

class StubHttpClient : HttpClient {

    lateinit var url: String
        private set

    private lateinit var body: String

    val queryParamMap = mutableMapOf<String, String>()

    val headerMap = mutableMapOf<String, String>()

    private val response = mutableListOf<HttpResponse>()

    override suspend fun get(url: String, block: HttpRequestScopeBuilder.() -> Unit): HttpResponse {
        this.url = url
        this.body = Gson().toJson(this)
        return response.first()
    }

    override suspend fun post(url: String, block: HttpRequestScopeBuilder.() -> Unit): HttpResponse {
        this.url = url
        this.body = Gson().toJson(this)
        return response.first()
    }

    override suspend fun put(url: String, block: HttpRequestScopeBuilder.() -> Unit): HttpResponse {
        this.url = url
        this.body = Gson().toJson(this)
        return response.first()
    }

    override suspend fun delete(url: String, block: HttpRequestScopeBuilder.() -> Unit): HttpResponse {
        this.url = url
        this.body = Gson().toJson(this)
        return response.first()
    }

    override suspend fun patch(url: String, block: HttpRequestScopeBuilder.() -> Unit): HttpResponse {
        this.url = url
        this.body = Gson().toJson(this)
        return response.first()
    }

    fun addResponse(response: HttpResponse) {
        this.response.add(response)
    }

    fun clear() {
        queryParamMap.clear()
        headerMap.clear()
        response.clear()
    }
}
