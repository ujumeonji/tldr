package run.cd80.tldr.api.manager.github.dto

object CreateTree {

    data class Command(val baseTree: String, val trees: List<CreateTreeItem>)
}
