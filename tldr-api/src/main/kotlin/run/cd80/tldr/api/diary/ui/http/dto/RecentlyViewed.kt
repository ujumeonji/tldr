package run.cd80.tldr.api.diary.ui.http.dto

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import java.time.LocalDateTime

object RecentlyViewed {

    data class Request(
        @field:Min(1)
        @field:Max(5)
        val count: Int,
    )

    data class Response(
        val diaries: List<Diary>,
    ) {

        data class Diary(
            val id: Long,
            val title: String,
            val content: String,
            val createdAt: LocalDateTime,
        )
    }
}
