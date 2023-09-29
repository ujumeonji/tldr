package run.cd80.tldr.core.http

interface HttpClient {

    fun get(url: String): HttpClientOption

    fun post(url: String): HttpClientOption

    fun put(url: String): HttpClientOption

    fun delete(url: String): HttpClientOption

    fun patch(url: String): HttpClientOption
}
