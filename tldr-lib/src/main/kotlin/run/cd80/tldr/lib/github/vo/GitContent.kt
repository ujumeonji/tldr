package run.cd80.tldr.lib.github.vo

import run.cd80.tldr.lib.github.type.EncodeType

interface GitContent {

    fun contentData(): String

    fun encodeType(): EncodeType
}
