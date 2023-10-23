package run.cd80.tldr.api.credential.ui.http.dto

object CreateGithubCredentialDto {

    data class Request(
        val code: String,
    )
}
