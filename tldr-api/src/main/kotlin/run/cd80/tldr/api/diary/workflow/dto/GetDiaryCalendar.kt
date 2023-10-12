package run.cd80.tldr.api.diary.workflow.dto

import java.time.LocalDateTime

object GetDiaryCalendar {

    data class Request(
        val username: String,
        val now: LocalDateTime,
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
