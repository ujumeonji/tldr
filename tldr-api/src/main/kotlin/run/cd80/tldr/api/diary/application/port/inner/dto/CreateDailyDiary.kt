package run.cd80.tldr.api.diary.application.port.inner.dto

import run.cd80.tldr.api.domain.user.Account
import java.time.LocalDateTime

object CreateDailyDiary {

    data class Command(
        val account: Account,
        val title: String,
        val content: String,
        val date: LocalDateTime,
    )
}
