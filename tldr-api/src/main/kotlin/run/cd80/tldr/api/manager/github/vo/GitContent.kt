package run.cd80.tldr.api.manager.github.vo

import run.cd80.tldr.api.manager.github.type.EncodeType

interface GitContent {

    fun contentData(): String

    fun encodeType(): EncodeType
}
