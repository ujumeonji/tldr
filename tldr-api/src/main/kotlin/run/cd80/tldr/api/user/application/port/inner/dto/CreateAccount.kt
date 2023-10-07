package run.cd80.tldr.api.user.application.port.inner.dto

object CreateAccount {

    data class Command(
        val email: String,
        val identifier: String,
    )
}
