package run.cd80.tldr.lib.github.dto

import run.cd80.tldr.lib.github.vo.GitContent

object UploadFile {

    data class Command(
        val message: String,
        val content: GitContent,
        val path: String,
    )
}
