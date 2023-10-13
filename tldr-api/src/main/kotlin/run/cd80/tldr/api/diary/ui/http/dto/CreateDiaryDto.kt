package run.cd80.tldr.api.diary.ui.http.dto

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Pattern

object CreateDiaryDto {

    data class Request(
        @field:NotEmpty
        val title: String,
        @field:NotEmpty
        val content: String,
        @field:Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$")
        val date: String,
    ) {

        val dateTime = "$date 00:00:00"
    }

    data class Response(
        val id: Long,
        val title: String,
        val content: String,
    )
}
