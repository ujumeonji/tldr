package run.cd80.tldr.api.manager.github.vo

object GitCommit {

    data class SHA(val value: String) {

        override fun toString(): String = value
    }
}
