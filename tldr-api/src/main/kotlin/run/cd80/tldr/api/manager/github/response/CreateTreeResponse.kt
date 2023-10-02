package run.cd80.tldr.api.manager.github.response

class CreateTreeResponse {

    val sha: String = ""

    val url: String = ""

    val tree: List<CreateTreeItemResponse> = listOf()

    val truncated: Boolean = false

    class CreateTreeItemResponse {

        val path: String = ""

        val mode: String = ""

        val type: String = ""

        val sha: String = ""

        val size: Int = 0

        val url: String = ""
    }
}
