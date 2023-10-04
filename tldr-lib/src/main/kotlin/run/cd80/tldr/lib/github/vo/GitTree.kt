package run.cd80.tldr.lib.github.vo

object GitTree {

    data class SHA(val value: String) {

        override fun toString(): String = value
    }
}
