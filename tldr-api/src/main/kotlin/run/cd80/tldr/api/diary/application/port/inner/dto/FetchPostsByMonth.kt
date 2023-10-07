package run.cd80.tldr.api.diary.application.port.inner.dto

import run.cd80.tldr.api.domain.user.vo.AccountId
import java.time.LocalDate

object FetchPostsByMonth {

    data class Command(
        val nowDate: LocalDate,
        val accountId: AccountId,
    )
}
