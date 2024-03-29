package run.cd80.tldr.api.credential.ui.http.dto

import run.cd80.tldr.api.credential.workflow.dto.RegisterProvider

object CreateCredentialDto {

    data class Request(
        val provider: String,
        val accessToken: String?,
        val owner: String?,
        val repository: String?,
        val apiKey: String?,
    ) {

        fun toCommand(username: String) =
            RegisterProvider.Request(
                username = username,
                provider = provider,
                accessToken = accessToken,
                apiKey = apiKey,
                owner = owner,
                repository = repository,
            )
    }

    data class Response(
        val id: Long,
    )
}
