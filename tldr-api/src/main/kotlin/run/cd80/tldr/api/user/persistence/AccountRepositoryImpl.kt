package run.cd80.tldr.api.user.persistence

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.stereotype.Repository
import run.cd80.tldr.api.user.application.port.outer.AccountQueryRepository
import run.cd80.tldr.api.user.application.port.outer.AccountRepository
import run.cd80.tldr.domain.user.Account

@Repository
class AccountRepositoryImpl(
    @PersistenceContext
    private val entityManager: EntityManager,
) : AccountQueryRepository, AccountRepository {

    override fun findByEmail(email: String): Account? =
        entityManager
            .createQuery("SELECT a FROM Account a WHERE a.email = :email AND a.deletedAt IS NULL", Account::class.java)
            .setParameter("email", email)
            .resultList
            .firstOrNull()

    override fun findByUsername(username: String): Account? =
        entityManager
            .createQuery("SELECT a FROM Account a WHERE a.username = :username AND a.deletedAt IS NULL", Account::class.java)
            .setParameter("username", username)
            .resultList
            .firstOrNull()

    override fun save(account: Account) =
        entityManager.persist(account)
}
