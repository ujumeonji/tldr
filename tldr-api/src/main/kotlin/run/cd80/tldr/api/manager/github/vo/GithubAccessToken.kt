package run.cd80.tldr.api.manager.github.vo

data class GithubAccessToken(val value: String) {

    companion object {

        val EMPTY = GithubAccessToken("")

        fun of(accessToken: String): GithubAccessToken {
            return GithubAccessToken(accessToken)
        }
    }
}
