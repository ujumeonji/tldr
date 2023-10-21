package run.cd80.tldr.api.credential.application.port.inner.dto

import run.cd80.tldr.domain.credential.Credential

interface RegistryCommand {

    fun toEntity(): Credential
}
