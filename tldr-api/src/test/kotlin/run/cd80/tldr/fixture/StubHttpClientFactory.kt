package run.cd80.tldr.fixture

import run.cd80.tldr.core.http.HttpClient
import run.cd80.tldr.core.http.HttpClientFactory

class StubHttpClientFactory(private val httpClient: HttpClient) : HttpClientFactory {

    override fun create(): HttpClient = httpClient
}
