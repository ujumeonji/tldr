package run.cd80.tldr.api.credential.repository

import org.springframework.data.repository.CrudRepository
import run.cd80.tldr.api.domain.credential.Credential

interface CredentialJpaRepository : CrudRepository<Credential, Long>
