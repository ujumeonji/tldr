package run.cd80.tldr.api.manager.github.vo

data class GithubCode(private val value: String) {

    companion object {

        fun of(value: String): GithubCode {
            return GithubCode(value)
        }
    }
}
