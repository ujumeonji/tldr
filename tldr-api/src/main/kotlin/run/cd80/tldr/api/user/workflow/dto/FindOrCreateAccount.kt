package run.cd80.tldr.api.user.workflow.dto

object FindOrCreateAccount {

    data class Request(
        val email: String,
        val identifier: String,
    )

    data class Response(
        val email: String,
    )
}
