package run.cd80.tldr.api.user.workflow

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldNotBe
import jakarta.persistence.EntityManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import run.cd80.tldr.api.user.workflow.dto.FindOrCreateAccount

@SpringBootTest
class FindOrCreateAccountWorkflowTest @Autowired constructor(
    private val workflow: FindOrCreateAccountWorkflow,
    private val entityManager: EntityManager,
) : DescribeSpec({

    describe("execute") {
        it("should return an existing account") {
            // given
            val email = "test@example.com"
            val identifier = "test-identifier"

            // when
            val response = workflow.execute(
                FindOrCreateAccount.Request(
                    email,
                    identifier,
                ),
            )

            // then
            response shouldNotBe null
        }
    }
})
