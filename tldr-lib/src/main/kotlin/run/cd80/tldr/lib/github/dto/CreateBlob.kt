package run.cd80.tldr.lib.github.dto

import run.cd80.tldr.lib.github.type.EncodeType

object CreateBlob {

    data class Command(val content: String, val path: String, val encoding: EncodeType = EncodeType.BASE64) {

        init {
            require(content.isNotBlank()) { "content must not be blank" }
            require(path.isNotBlank()) { "path must not be blank" }
        }
    }
}
