package run.cd80.tldr.api.credential.application.port.inner.dto

import run.cd80.tldr.domain.credential.Credential
import run.cd80.tldr.domain.credential.GithubCredential
import run.cd80.tldr.domain.user.Account

data class GithubRegistryCommand(
    private val account: Account,
    private val accessToken: String,
    private val username: String,
    private val repository: String,
) : RegistryCommand {

    override fun toEntity(): Credential {
        return GithubCredential.of(account, accessToken, username, repository)
    }
}
