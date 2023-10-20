package run.cd80.tldr.api.credential.application.port

import org.springframework.stereotype.Service
import run.cd80.tldr.api.credential.application.port.inner.CredentialService
import run.cd80.tldr.api.credential.application.port.inner.dto.RegistryCommand
import run.cd80.tldr.api.credential.application.port.out.CredentialRepository
import run.cd80.tldr.domain.credential.Credential

@Service
class CredentialDomainService(
    private val credentialRepository: CredentialRepository,
) : CredentialService {

    override fun register(registryCommand: RegistryCommand): Credential {
        return credentialRepository.save(registryCommand.toEntity())
    }
}
