package run.cd80.tldr.api.diary.ui.http.dto

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Pattern
import java.time.LocalDateTime

object DailyCalendar {

    data class Request(
        @field:NotEmpty
        @field:Pattern(regexp = "^(19|20)\\d{2}$")
        val year: String,
        @field:NotEmpty
        @field:Pattern(regexp = "^(0[1-9]|1[0-2])$")
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
            val diaryAt: LocalDateTime,
            val createdAt: LocalDateTime,
        )
    }
}
