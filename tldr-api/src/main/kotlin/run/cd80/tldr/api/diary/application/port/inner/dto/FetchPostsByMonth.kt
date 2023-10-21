package run.cd80.tldr.api.diary.application.port.inner.dto

import run.cd80.tldr.domain.user.vo.AccountId
import java.time.LocalDateTime

object FetchPostsByMonth {

    data class Command(
        val now: LocalDateTime,
        val accountId: AccountId,
    )
}
