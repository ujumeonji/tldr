package run.cd80.tldr.api.diary.workflow.dto

import java.time.LocalDateTime

object GetDiaryBySlug {

    data class Request(
        val slug: String,
    )

    data class Response(
        val id: Long,
        val title: String,
        val diaryAt: LocalDateTime,
        val createdAt: LocalDateTime,
    )
}
