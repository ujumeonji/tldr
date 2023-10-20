package run.cd80.tldr.api.credential.application.port.inner.dto

import run.cd80.tldr.api.domain.credential.Credential
import run.cd80.tldr.api.domain.credential.GithubCredential
import run.cd80.tldr.api.domain.user.Account

data class GithubRegistryCommand(private val account: Account, private val accessToken: String) : RegistryCommand {

    override fun toEntity(): Credential {
        return GithubCredential.of(account, accessToken)
    }
}
