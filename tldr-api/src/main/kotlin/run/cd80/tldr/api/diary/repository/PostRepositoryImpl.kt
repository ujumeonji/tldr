package run.cd80.tldr.api.diary.repository

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.stereotype.Repository
import run.cd80.tldr.api.diary.application.port.out.PostQueryRepository
import run.cd80.tldr.api.domain.post.Post
import run.cd80.tldr.api.domain.user.vo.AccountId
import java.time.LocalDateTime

@Repository
class PostRepositoryImpl(
    @PersistenceContext
    private val entityManager: EntityManager,
) : PostQueryRepository {

    override fun findByMonth(accountId: AccountId, date: LocalDateTime): List<Post> {
        val start = date.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0)
        val end = date.withDayOfMonth(date.toLocalDate().lengthOfMonth()).withHour(23).withMinute(59).withSecond(59)
        return entityManager
            .createQuery(
                "SELECT p FROM Post p JOIN Account a ON a.id = p.account.id WHERE p.account.id = :accountId AND p.createdAt BETWEEN :start AND :end AND p.deletedAt IS NULL",
                Post::class.java,
            )
            .setParameter("accountId", accountId.id)
            .setParameter("start", start)
            .setParameter("end", end)
            .resultList
    }

    override fun findRecentViewed(accountId: AccountId, count: Int): List<Post> {
        return entityManager
            .createQuery(
                "SELECT p FROM Post p JOIN Account a ON a.id = p.account.id WHERE p.account.id = :accountId AND p.deletedAt IS NULL ORDER BY p.viewedAt DESC",
                Post::class.java,
            )
            .setParameter("accountId", accountId.id)
            .setMaxResults(count)
            .resultList
    }
}
