package run.cd80.tldr.api.credential.application.port.inner

import run.cd80.tldr.api.credential.application.port.inner.dto.RegistryCommand
import run.cd80.tldr.domain.credential.Credential
import run.cd80.tldr.domain.credential.GithubCredential
import run.cd80.tldr.domain.user.Account

interface CredentialService {

    fun register(registryCommand: RegistryCommand): Credential

    fun findGithubByAccount(account: Account): GithubCredential?
}
