package run.cd80.tldr.api.credential.application.port.out

import run.cd80.tldr.api.domain.credential.Credential

interface CredentialRepository {

    fun save(credential: Credential): Credential
}
