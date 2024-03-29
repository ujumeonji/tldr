package run.cd80.tldr.api.diary.application.port.outer

import run.cd80.tldr.domain.post.Post
import run.cd80.tldr.domain.user.vo.AccountId
import java.time.LocalDateTime

interface PostQueryRepository {

    fun findByMonth(accountId: AccountId, date: LocalDateTime): List<Post>

    fun findRecentlyViewed(accountId: AccountId, count: Int = 5): List<Post>

    fun findByDate(accountId: AccountId, date: LocalDateTime): List<Post>
}
