package run.cd80.tldr.core.http

interface HttpClientFactory {

    fun create(): HttpClient
}
