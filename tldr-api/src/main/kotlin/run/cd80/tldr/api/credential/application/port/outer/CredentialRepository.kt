package run.cd80.tldr.api.credential.application.port.outer

import run.cd80.tldr.domain.credential.Credential

interface CredentialRepository {

    fun save(credential: Credential): Credential
}
