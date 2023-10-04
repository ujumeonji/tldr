package run.cd80.tldr.lib.github.vo

object GitCommit {

    data class SHA(val value: String) {

        override fun toString(): String = value
    }
}
