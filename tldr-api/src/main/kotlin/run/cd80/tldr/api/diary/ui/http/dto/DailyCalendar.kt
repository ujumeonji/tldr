package run.cd80.tldr.api.diary.ui.http.dto

import java.time.LocalDate
import java.time.LocalDateTime

object DailyCalendar {

    data class Request(
        val month: String,
    )

    data class Response(
        val date: LocalDateTime,
        val diaries: List<Diary>,
    )

    data class Diary(
        val id: Long,
        val title: String,
        val createdAt: LocalDate,
    )
}
