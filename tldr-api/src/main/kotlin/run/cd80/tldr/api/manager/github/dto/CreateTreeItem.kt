package run.cd80.tldr.api.manager.github.dto

data class CreateTreeItem(
    val path: String,
    val mode: String,
    val type: String,
    val sha: String,
) {

    fun toMap() = mapOf(
        "path" to path,
        "mode" to mode,
        "type" to type,
        "sha" to sha,
    )

    companion object {

        fun of(path: String, mode: String, type: String, sha: String): CreateTreeItem {
            return CreateTreeItem(path, mode, type, sha)
        }
    }
}
