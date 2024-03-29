package run.cd80.tldr.api.diary.workflow.dto

import java.time.LocalDateTime

object GetRecentlyViewed {

    data class Request(
        val username: String,
        val count: Int,
    )

    data class Response(
        val items: List<Item>,
    ) {

        data class Item(
            val id: Long,
            val title: String,
            val content: String,
            val createdAt: LocalDateTime,
        )
    }
}
