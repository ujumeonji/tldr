package run.cd80.tldr.api.user.persistence

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import jakarta.persistence.EntityManager
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import run.cd80.tldr.api.domain.user.Account

@Transactional
@SpringBootTest
class AccountRepositoryImplTest @Autowired constructor(
    private val accountRepository: AccountRepositoryImpl,
    private val entityManager: EntityManager,
) : DescribeSpec({

    describe("save") {
        it("should persist an account") {
            // given
            val account = Account.signUp("test@example.com", "test")

            // when
            accountRepository.save(account)

            // then
            entityManager.find(Account::class.java, account.id) shouldNotBe null
        }
    }

    describe("findByEmail") {
        it("should return an account") {
            // given
            val account = Account.signUp("test@example.com", "test")
            entityManager.persist(account)

            // when
            val result = accountRepository.findByEmail("test@example.com")

            // then
            result shouldNotBe null
            result shouldBe account
        }
    }
})
