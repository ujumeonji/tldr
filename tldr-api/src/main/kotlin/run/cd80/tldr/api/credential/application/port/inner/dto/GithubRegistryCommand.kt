package run.cd80.tldr.api.credential.application.port.inner.dto

import run.cd80.tldr.api.domain.credential.Credential
import run.cd80.tldr.api.domain.credential.GithubCredential

data class GithubRegistryCommand(private val accessToken: String) : RegistryCommand {

    override fun toEntity(): Credential {
        return GithubCredential.of(accessToken)
    }
}
