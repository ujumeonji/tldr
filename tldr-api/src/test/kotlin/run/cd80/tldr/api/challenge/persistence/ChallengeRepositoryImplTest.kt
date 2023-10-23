package run.cd80.tldr.api.challenge.persistence

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.longs.shouldBeGreaterThan
import jakarta.persistence.EntityManager
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import run.cd80.tldr.common.createAccount
import run.cd80.tldr.common.createBojChallenge
import run.cd80.tldr.config.QueryDSLTestConfig
import java.time.LocalDateTime

@Transactional
@Import(ChallengeRepositoryImpl::class, QueryDSLTestConfig::class)
@DataJpaTest
class ChallengeRepositoryImplTest @Autowired constructor(
    private val repository: ChallengeRepositoryImpl,
    private val entityManager: EntityManager,
) : StringSpec() {

    init {

        "백준 정보를 저장한다." {
            // given
            val now = LocalDateTime.of(2023, 10, 1, 0, 0, 0)
            val account = entityManager.createAccount(createdAt = now)
            val challenge = entityManager.createBojChallenge(account = account)

            // when
            val result = repository.save(challenge)

            // then
            result.id shouldBeGreaterThan 0
        }
    }
}
