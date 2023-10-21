package run.cd80.tldr.api.credential.persistence

import org.springframework.data.repository.CrudRepository
import run.cd80.tldr.domain.credential.Credential

interface CredentialJpaRepository : CrudRepository<Credential, Long>
