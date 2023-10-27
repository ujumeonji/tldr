package run.cd80.tldr.api.credential.application.port.inner.dto

import run.cd80.tldr.domain.credential.Credential
import run.cd80.tldr.domain.credential.GithubCredential
import run.cd80.tldr.domain.user.Account
import java.time.LocalDateTime

data class GithubRegistryCommand(
    private val account: Account,
    private val accessToken: String,
    private val registerAt: LocalDateTime,
) : RegistryCommand {

    override fun toEntity(): Credential {
        return GithubCredential.of(account, accessToken, registerAt)
    }
}
