package run.cd80.tldr.api.diary.application.port.out

import run.cd80.tldr.api.domain.post.Post
import run.cd80.tldr.api.domain.user.vo.AccountId
import java.time.LocalDate

interface PostQueryRepository {

    fun findByMonth(accountId: AccountId, date: LocalDate): List<Post>
}
