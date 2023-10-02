package run.cd80.tldr.api.manager.github.vo

data class GithubAccessToken(private val value: String) {

    override fun toString(): String =
        value

    companion object {

        val EMPTY = GithubAccessToken("")

        fun of(accessToken: String): GithubAccessToken {
            return GithubAccessToken(accessToken)
        }
    }
}
