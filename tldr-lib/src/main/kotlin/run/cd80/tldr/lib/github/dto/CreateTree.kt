package run.cd80.tldr.lib.github.dto

object CreateTree {

    data class Command(val baseTree: String, val trees: List<CreateTreeItem>)
}
