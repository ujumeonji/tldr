package run.cd80.tldr.api.diary.repository

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.longs.shouldBeGreaterThan
import jakarta.persistence.EntityManager
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import run.cd80.tldr.api.domain.post.Post
import run.cd80.tldr.common.createAccount
import java.time.LocalDateTime

@Transactional
@Import(PostRepositoryImpl::class)
@DataJpaTest
class PostRepositoryImplTest @Autowired constructor(
    private val postRepository: PostRepositoryImpl,
    private val entityManager: EntityManager,
) : StringSpec() {

    init {

        "새 글을 저장한다." {
            // given
            val now = LocalDateTime.of(2023, 10, 1, 0, 0, 0)
            val account = entityManager.createAccount(createdAt = now)
            val post = Post.builder().account(account).createdAt(now).build()

            // when
            val result = postRepository.save(post)

            // then
            result.id shouldBeGreaterThan 0
        }
    }
}
