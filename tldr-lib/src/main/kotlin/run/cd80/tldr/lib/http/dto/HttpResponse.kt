package run.cd80.tldr.lib.http.dto

data class HttpResponse(
    val statusCode: Int,
    val body: String,
)
