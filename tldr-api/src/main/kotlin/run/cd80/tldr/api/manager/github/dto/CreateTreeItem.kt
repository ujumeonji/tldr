package run.cd80.tldr.api.manager.github.dto

import run.cd80.tldr.api.manager.github.vo.GitBlob

data class CreateTreeItem(
    val sha: GitBlob.SHA,
    val path: String,
    val mode: String = "100644",
    val type: String = "blob",
) {

    fun toMap() = mapOf(
        "path" to path,
        "mode" to mode,
        "type" to type,
        "sha" to sha.toString(),
    )

    companion object {

        fun of(path: String, mode: String, type: String, sha: String): CreateTreeItem {
            return CreateTreeItem(GitBlob.SHA(sha), path, mode, type)
        }
    }
}
