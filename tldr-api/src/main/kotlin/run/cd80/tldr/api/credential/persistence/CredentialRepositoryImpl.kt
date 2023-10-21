package run.cd80.tldr.api.credential.persistence

import org.springframework.stereotype.Repository
import run.cd80.tldr.api.credential.application.port.outer.CredentialRepository
import run.cd80.tldr.domain.credential.Credential

@Repository
class CredentialRepositoryImpl(
    private val repository: CredentialJpaRepository,
) : CredentialRepository {

    override fun save(credential: Credential): Credential {
        return repository.save(credential)
    }
}
