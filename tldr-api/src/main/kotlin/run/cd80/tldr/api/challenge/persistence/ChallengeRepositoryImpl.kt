package run.cd80.tldr.api.challenge.persistence

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import run.cd80.tldr.api.challenge.port.outer.ChallengeQueryRepository
import run.cd80.tldr.api.challenge.port.outer.ChallengeRepository
import run.cd80.tldr.domain.challenge.BojChallenge
import run.cd80.tldr.domain.challenge.Challenge
import run.cd80.tldr.domain.challenge.QBojChallenge.bojChallenge
import run.cd80.tldr.domain.user.Account

@Repository
class ChallengeRepositoryImpl(
    private val repository: ChallengeJpaRepository,
    private val jpaQueryFactory: JPAQueryFactory,
) : ChallengeRepository, ChallengeQueryRepository {

    override fun findBojByAccount(account: Account): BojChallenge? =
        jpaQueryFactory
            .selectFrom(bojChallenge)
            .where(bojChallenge.account.eq(account).and(bojChallenge.deletedAt.isNull()))
            .fetchOne()

    override fun save(challenge: Challenge): Challenge =
        repository.save(challenge)
}
