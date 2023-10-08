package run.cd80.tldr.api.diary.ui.http.dto

import java.time.LocalDateTime

object RecentViewed {

    data class Request(
        val count: Int,
    )

    data class Response(
        val date: LocalDateTime,
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
