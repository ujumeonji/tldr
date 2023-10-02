package run.cd80.tldr.api.manager.github.dto

object UploadFile {

    data class Command(
        val message: String,
        val content: String,
        val branch: String,
        val path: String,
    )
}
