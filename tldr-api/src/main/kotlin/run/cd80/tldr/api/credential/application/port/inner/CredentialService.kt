package run.cd80.tldr.api.credential.application.port.inner

import run.cd80.tldr.api.credential.application.port.inner.dto.RegistryCommand
import run.cd80.tldr.api.domain.credential.Credential

interface CredentialService {

    fun register(registryCommand: RegistryCommand): Credential
}
