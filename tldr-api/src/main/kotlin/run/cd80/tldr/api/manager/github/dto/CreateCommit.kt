package run.cd80.tldr.api.manager.github.dto

object CreateCommit {

    data class Command(val message: String, val tree: String, val parents: List<String> = emptyList())
}
