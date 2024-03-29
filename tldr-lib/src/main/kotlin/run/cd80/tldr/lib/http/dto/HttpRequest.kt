package run.cd80.tldr.lib.http.dto

data class HttpRequest(
    val uri: String?,
    val parameters: List<Pair<String, String>>,
    val headers: List<Pair<String, String>>,
    val body: Any?,
)
