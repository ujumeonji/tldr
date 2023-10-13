package run.cd80.tldr.api.diary.workflow.dto

import java.time.LocalDateTime

object CreateDiary {

    data class Request(
        val username: String,
        val title: String,
        val content: String,
        val date: LocalDateTime,
    )

    data class Response(
        val id: Long,
        val title: String,
        val content: String,
        val createdAt: LocalDateTime,
    )
}
