package run.cd80.tldr.api.manager.github.dto

object UpdateHead {

    data class Command(val branch: String, val sha: String, val force: Boolean = false)
}
