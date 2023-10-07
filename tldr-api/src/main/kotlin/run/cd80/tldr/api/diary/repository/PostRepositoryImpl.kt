package run.cd80.tldr.api.diary.repository

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.stereotype.Repository
import run.cd80.tldr.api.diary.application.port.out.PostQueryRepository
import run.cd80.tldr.api.domain.post.Post
import run.cd80.tldr.api.domain.user.vo.AccountId
import java.time.LocalDate

@Repository
class PostRepositoryImpl(
    @PersistenceContext
    private val entityManager: EntityManager,
) : PostQueryRepository {

    override fun findByMonth(accountId: AccountId, date: LocalDate): List<Post> {
        val start = date.withDayOfMonth(1)
        val end = date.withDayOfMonth(date.lengthOfMonth())
        return entityManager
            .createQuery(
                "SELECT p FROM Post p JOIN Account a WHERE a.id = :accountId AND p.createdAt BETWEEN :start AND :end AND p.deletedAt IS NULL",
                Post::class.java,
            )
            .setParameter("accountId", accountId.id)
            .setParameter("start", start)
            .setParameter("end", end)
            .resultList
    }
}
