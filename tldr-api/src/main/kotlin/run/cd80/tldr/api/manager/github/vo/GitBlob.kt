package run.cd80.tldr.api.manager.github.vo

object GitBlob {

    data class SHA(val value: String) {

        override fun toString(): String = value
    }
}
