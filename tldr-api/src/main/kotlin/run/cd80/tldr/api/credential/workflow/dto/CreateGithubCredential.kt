package run.cd80.tldr.api.credential.workflow.dto

object CreateGithubCredential {

    data class Request(
        val username: String,
        val code: String,
    )

    data class Response(
        val id: Long,
    )
}
