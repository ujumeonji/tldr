package run.cd80.tldr.api.diary.persistence

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import run.cd80.tldr.api.diary.application.port.outer.PostQueryRepository
import run.cd80.tldr.domain.post.Post
import run.cd80.tldr.domain.post.QPost.post
import run.cd80.tldr.domain.user.QAccount.account
import run.cd80.tldr.domain.user.vo.AccountId
import java.time.LocalDateTime

@Repository
class PostQueryRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory,
) : PostQueryRepository {

    override fun findByMonth(accountId: AccountId, now: LocalDateTime): List<Post> {
        val start = now.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0)
        val end = now.withDayOfMonth(now.toLocalDate().lengthOfMonth()).withHour(23).withMinute(59).withSecond(59)

        return jpaQueryFactory
            .selectFrom(post)
            .join(post.account, account)
            .where(
                post.account.username.eq(accountId.id),
                post.account.deletedAt.isNull,
                post.deletedAt.isNull,
                post.createdAt.between(start, end),
            )
            .orderBy(post.createdAt.desc())
            .fetch()
    }

    override fun findRecentlyViewed(accountId: AccountId, count: Int): List<Post> {
        return jpaQueryFactory
            .selectFrom(post)
            .join(post.account, account)
            .where(
                post.account.username.eq(accountId.id),
                post.account.deletedAt.isNull,
                post.deletedAt.isNull,
            )
            .orderBy(post.createdAt.desc())
            .limit(count.toLong())
            .fetch()
    }

    override fun findByDate(accountId: AccountId, date: LocalDateTime): List<Post> {
        val start = date.withHour(0).withMinute(0).withSecond(0)
        val end = date.withHour(23).withMinute(59).withSecond(59)

        return jpaQueryFactory
            .selectFrom(post)
            .join(post.account, account)
            .where(
                post.account.username.eq(accountId.id),
                post.account.deletedAt.isNull,
                post.deletedAt.isNull,
                post.createdAt.between(start, end),
            )
            .orderBy(post.createdAt.desc())
            .fetch()
    }
}
