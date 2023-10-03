package run.cd80.tldr.api.manager.github.dto

import run.cd80.tldr.api.manager.github.vo.GitContent

object UploadFile {

    data class Command(
        val message: String,
        val content: GitContent,
        val branch: String,
        val path: String,
    )
}
