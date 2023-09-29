package run.cd80.tldr.core.http.dto

data class HttpResponse(
    val statusCode: Int,
    val body: String,
)
