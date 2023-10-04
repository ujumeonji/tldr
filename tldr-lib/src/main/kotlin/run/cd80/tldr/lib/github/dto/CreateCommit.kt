package run.cd80.tldr.lib.github.dto

import run.cd80.tldr.lib.github.vo.GitTree

object CreateCommit {

    data class Command(val message: String, val tree: GitTree.SHA, val parents: List<String> = emptyList())
}
