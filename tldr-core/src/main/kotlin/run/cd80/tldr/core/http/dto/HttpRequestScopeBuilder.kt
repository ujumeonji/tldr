package run.cd80.tldr.core.http.dto

class HttpRequestScopeBuilder {
    private var parameters: MutableList<Pair<String, String>> = mutableListOf()
    private var headers: MutableList<Pair<String, String>> = mutableListOf()

    private var uri: String = ""
    private var body: Any = ""

    fun parameter(key: String, value: String) {
        parameters += key to value
    }

    fun header(key: String, value: String) {
        headers += key to value
    }

    fun body(data: Any) {
        body = data
    }

    fun build(): HttpRequest =
        HttpRequest(uri, parameters, headers, body)
}
