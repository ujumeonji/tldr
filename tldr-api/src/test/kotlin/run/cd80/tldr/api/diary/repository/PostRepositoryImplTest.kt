package run.cd80.tldr.api.diary.repository

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import jakarta.persistence.EntityManager
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import run.cd80.tldr.api.domain.post.Post
import run.cd80.tldr.api.domain.user.Account
import run.cd80.tldr.api.domain.user.vo.AccountId
import java.time.LocalDateTime

@Transactional
@SpringBootTest
class PostRepositoryImplTest @Autowired constructor(
    private val postRepository: PostRepositoryImpl,
    private val entityManager: EntityManager,
) : DescribeSpec({

    describe("findByMonth") {
        it("주어진 달에 작성된 게시글을 조회한다.") {
            // given
            val now = LocalDateTime.now()
            val account = Account.signUp("123", "123")
            entityManager.persist(account)
            val post = Post.create("test-title", "test-content", account)
            entityManager.persist(post)

            // when
            val result = postRepository.findByMonth(AccountId.of(account.id), now)

            // then
            result shouldNotBe null
            result.size shouldBe 1
            result[0] shouldBe post
        }
    }
})
