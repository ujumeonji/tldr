package run.cd80.tldr.api.diary.repository

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import jakarta.persistence.EntityManager
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import run.cd80.tldr.api.domain.user.vo.AccountId
import run.cd80.tldr.common.createAccount
import run.cd80.tldr.common.createPost
import java.time.LocalDateTime

@Transactional
@SpringBootTest
class PostRepositoryImplTest @Autowired constructor(
    private val postRepository: PostRepositoryImpl,
    private val entityManager: EntityManager,
) : StringSpec() {

    init {

        "주어진 달에 작성된 게시글을 조회한다." {
            // given
            val now = LocalDateTime.of(2023, 10, 1, 0, 0, 0)
            val account = entityManager.createAccount(createdAt = now)
            val post = entityManager.createPost(account = account, createdAt = now)

            // when
            val result = postRepository.findByMonth(AccountId.of(account.username), now)

            // then
            result shouldNotBe null
            result shouldHaveSize 1
            result[0] shouldBe post
        }

        "범위에서 제외된 게시글은 조회되지 않는다." {
            // given
            val now = LocalDateTime.of(2023, 10, 1, 0, 0, 0)
            val account = entityManager.createAccount(createdAt = now)
            entityManager.createPost(account = account, createdAt = now.minusSeconds(1))
            entityManager.createPost(account = account, createdAt = now.plusMonths(1))

            // when
            val result = postRepository.findByMonth(AccountId.of(account.username), now)

            // then
            result shouldHaveSize 0
        }
    }
}
