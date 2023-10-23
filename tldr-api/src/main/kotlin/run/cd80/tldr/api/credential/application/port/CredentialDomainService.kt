package run.cd80.tldr.api.credential.application.port

import org.springframework.stereotype.Service
import run.cd80.tldr.api.credential.application.port.inner.CredentialService
import run.cd80.tldr.api.credential.application.port.inner.dto.RegistryCommand
import run.cd80.tldr.api.credential.application.port.outer.CredentialQueryRepository
import run.cd80.tldr.api.credential.application.port.outer.CredentialRepository
import run.cd80.tldr.domain.credential.Credential
import run.cd80.tldr.domain.credential.GithubCredential
import run.cd80.tldr.domain.user.Account

@Service
class CredentialDomainService(
    private val credentialRepository: CredentialRepository,
    private val credentialQueryRepository: CredentialQueryRepository,
) : CredentialService {

    override fun register(registryCommand: RegistryCommand): Credential {
        return credentialRepository.save(registryCommand.toEntity())
    }

    override fun findGithubByAccount(account: Account): GithubCredential? =
        credentialQueryRepository.findGithubByAccount(account)
}
