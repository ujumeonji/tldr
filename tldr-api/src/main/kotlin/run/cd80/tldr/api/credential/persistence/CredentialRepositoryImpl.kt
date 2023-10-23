package run.cd80.tldr.api.credential.persistence

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import run.cd80.tldr.api.credential.application.port.outer.CredentialQueryRepository
import run.cd80.tldr.api.credential.application.port.outer.CredentialRepository
import run.cd80.tldr.domain.credential.Credential
import run.cd80.tldr.domain.credential.GithubCredential
import run.cd80.tldr.domain.credential.QGithubCredential.githubCredential
import run.cd80.tldr.domain.user.Account

@Repository
class CredentialRepositoryImpl(
    private val repository: CredentialJpaRepository,
    private val jpaQueryFactory: JPAQueryFactory,
) : CredentialRepository, CredentialQueryRepository {

    override fun save(credential: Credential): Credential =
        repository.save(credential)

    override fun findGithubByAccount(account: Account): GithubCredential? =
        jpaQueryFactory
            .selectFrom(githubCredential)
            .where(githubCredential.account.eq(account))
            .fetchOne()
}
