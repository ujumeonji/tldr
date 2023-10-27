package run.cd80.tldr.lib.github.vo

data class GithubCode(private val value: String) {

    override fun toString(): String = value

    companion object {

        fun of(value: String): GithubCode {
            return GithubCode(value)
        }
    }
}
