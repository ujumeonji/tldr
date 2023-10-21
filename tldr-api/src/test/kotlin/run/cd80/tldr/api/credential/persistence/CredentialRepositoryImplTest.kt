package run.cd80.tldr.api.credential.persistence

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.longs.shouldBeGreaterThan
import jakarta.persistence.EntityManager
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import run.cd80.tldr.common.createAccount
import run.cd80.tldr.common.createGithubCredential
import run.cd80.tldr.common.createWakaTimeCredential
import java.time.LocalDateTime

@Transactional
@Import(CredentialRepositoryImpl::class)
@DataJpaTest
class CredentialRepositoryImplTest @Autowired constructor(
    private val repository: CredentialRepositoryImpl,
    private val entityManager: EntityManager,
) : StringSpec() {

    init {

        "깃허브 인가 정보를 저장한다." {
            // given
            val now = LocalDateTime.of(2023, 10, 1, 0, 0, 0)
            val account = entityManager.createAccount(createdAt = now)
            val credential = entityManager.createGithubCredential(account = account)

            // when
            val result = repository.save(credential)

            // then
            result.id shouldBeGreaterThan 0
        }

        "Wakatime API 정보를 저장한다." {
            // given
            val now = LocalDateTime.of(2023, 10, 1, 0, 0, 0)
            val account = entityManager.createAccount(createdAt = now)
            val credential = entityManager.createWakaTimeCredential(account = account)

            // when
            val result = repository.save(credential)

            // then
            result.id shouldBeGreaterThan 0
        }
    }
}
