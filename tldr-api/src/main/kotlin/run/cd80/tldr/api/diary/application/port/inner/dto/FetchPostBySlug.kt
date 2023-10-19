package run.cd80.tldr.api.diary.application.port.inner.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty

object FetchPostBySlug {

    data class Command(@field:NotEmpty @field:NotBlank val slug: String)
}
