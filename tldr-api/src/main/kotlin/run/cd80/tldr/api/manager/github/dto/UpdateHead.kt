package run.cd80.tldr.api.manager.github.dto

import run.cd80.tldr.api.manager.github.vo.GitCommit

object UpdateHead {

    data class Command(val branch: String, val sha: GitCommit.SHA, val force: Boolean = false)
}
