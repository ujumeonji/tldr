package run.cd80.tldr.api.diary.workflow

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import jakarta.persistence.EntityManager
import jakarta.transaction.Transactional
import org.springframework.boot.test.context.SpringBootTest
import run.cd80.tldr.api.diary.workflow.dto.CreateDiary
import run.cd80.tldr.common.createAccount
import java.time.LocalDateTime

@Transactional
@SpringBootTest
class CreateDiaryWorkflowTest(
    private val workflow: CreateDiaryWorkflow,
    private val entityManager: EntityManager,
) : StringSpec() {

    init {

        "새 글을 작성한다." {
            // given
            val account = entityManager.createAccount()
            val command = CreateDiary.Request(
                username = account.username,
                title = "title",
                content = "content",
                date = LocalDateTime.of(2023, 10, 13, 0, 0),
            )

            // when
            val response = workflow.execute(command)

            // then
            response.id shouldBe 1L
            response.title shouldBe "title"
            response.content shouldBe "content"
        }
    }
}
