package run.cd80.tldr.api.manager.github.dto

data class CreateTreeItem(
    val sha: String,
    val path: String,
    val mode: String = "100644",
    val type: String = "blob",
) {

    fun toMap() = mapOf(
        "path" to path,
        "mode" to mode,
        "type" to type,
        "sha" to sha,
    )

    companion object {

        fun of(path: String, mode: String, type: String, sha: String): CreateTreeItem {
            return CreateTreeItem(sha, path, mode, type)
        }
    }
}
