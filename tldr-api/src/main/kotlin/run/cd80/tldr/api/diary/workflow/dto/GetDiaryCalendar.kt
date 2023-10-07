package run.cd80.tldr.api.diary.workflow.dto

import java.time.LocalDate
import java.time.LocalDateTime

object GetDiaryCalendar {

    data class Request(
        val accountId: Long,
        val nowDate: LocalDate,
    )

    data class Response(
        val items: List<Item>,
    ) {

        data class Item(
            val id: Long,
            val title: String,
            val createdDate: LocalDateTime,
        )
    }
}
