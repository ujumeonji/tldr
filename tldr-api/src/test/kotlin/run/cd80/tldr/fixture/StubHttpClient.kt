package run.cd80.tldr.fixture

import run.cd80.tldr.core.http.HttpClient
import run.cd80.tldr.core.http.HttpClientOption
import run.cd80.tldr.core.http.dto.HttpResponse

class StubHttpClient : HttpClient, HttpClientOption {

    lateinit var url: String
        private set

    private lateinit var body: String

    val queryParamMap = mutableMapOf<String, String>()

    val headerMap = mutableMapOf<String, String>()

    private val response = mutableListOf<HttpResponse>()

    override fun get(url: String): HttpClientOption =
        apply { this.url = url }

    override fun post(url: String): HttpClientOption =
        apply { this.url = url }

    override fun put(url: String): HttpClientOption =
        apply { this.url = url }

    override fun delete(url: String): HttpClientOption =
        apply { this.url = url }

    override fun patch(url: String): HttpClientOption =
        apply { this.url = url }

    override fun queryParam(key: String, value: String): HttpClientOption =
        apply { queryParamMap[key] = value }

    override fun header(key: String, value: String): HttpClientOption =
        apply { headerMap[key] = value }

    override fun body(body: String): HttpClientOption =
        apply { this.body = body }

    override suspend fun execute(): HttpResponse {
        val response = response.first()
        this.response.removeFirst()
        return response
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
