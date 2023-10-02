package run.cd80.tldr.api.manager.github.dto

import java.util.*

object CreateBlob {

    data class Command(val content: String, val path: String, val encoding: String = "base64") {

        init {
            require(content.isNotBlank()) { "content must not be blank" }
            require(path.isNotBlank()) { "path must not be blank" }
            require(encoding.isNotBlank()) { "encoding must not be blank" }
        }

        fun base64Encode(): String =
            Base64.getEncoder().encodeToString(content.toByteArray())
    }
}
