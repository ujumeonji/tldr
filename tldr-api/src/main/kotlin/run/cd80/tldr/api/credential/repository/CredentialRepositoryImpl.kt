package run.cd80.tldr.api.credential.repository

import org.springframework.stereotype.Repository
import run.cd80.tldr.api.credential.application.port.out.CredentialRepository
import run.cd80.tldr.api.domain.credential.Credential

@Repository
class CredentialRepositoryImpl(
    private val repository: CredentialJpaRepository,
) : CredentialRepository {

    override fun save(credential: Credential): Credential {
        return repository.save(credential)
    }
}
