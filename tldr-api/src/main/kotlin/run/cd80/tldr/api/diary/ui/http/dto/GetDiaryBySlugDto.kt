package run.cd80.tldr.api.diary.ui.http.dto

import jakarta.validation.constraints.NotEmpty

object GetDiaryBySlugDto {

    data class Request(
        @field:NotEmpty
        val title: String,
    )

    data class Response(
        val id: Long,
        val title: String,
    )
}
