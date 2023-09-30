package run.cd80.tldr.api.crawler.wakatime.dto

import java.time.LocalDate
import java.time.LocalDateTime

object GetTaskRecord {

    data class Command(val apiKey: String, val date: LocalDate) {

        init {
            require(apiKey.isNotBlank()) { "apiKey must not be blank" }
        }
    }

    data class Result(val project: String, val duration: Float, val workedDateTime: LocalDateTime) {

        init {
            require(duration > 0.0f) { "duration must be greater than 0" }
        }
    }
}
