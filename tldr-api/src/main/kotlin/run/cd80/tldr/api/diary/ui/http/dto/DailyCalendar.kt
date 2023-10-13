package run.cd80.tldr.api.diary.ui.http.dto

import java.time.LocalDateTime

object DailyCalendar {

    data class Request(
        val year: String,
        val month: String,
    ) {

        val now: LocalDateTime
            get() = LocalDateTime.of(year.toInt(), month.toInt(), 1, 0, 0)
    }

    data class Response(
        val date: LocalDateTime,
        val diaries: List<Diary>,
    ) {

        data class Diary(
            val id: Long,
            val title: String,
            val createdAt: LocalDateTime,
        )
    }
}
