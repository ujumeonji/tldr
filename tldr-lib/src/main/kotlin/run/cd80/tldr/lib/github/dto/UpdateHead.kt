package run.cd80.tldr.lib.github.dto

import run.cd80.tldr.lib.github.vo.GitCommit

object UpdateHead {

    data class Command(val branch: String, val sha: GitCommit.SHA, val force: Boolean = false)
}
