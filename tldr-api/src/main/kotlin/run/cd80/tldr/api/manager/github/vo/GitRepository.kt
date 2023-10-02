package run.cd80.tldr.api.manager.github.vo

data class GitRepository(private val name: String, private val owner: String) {

    fun getFullName(): String {
        return "$owner/$name"
    }

    companion object {

        fun of(owner: String, repo: String): GitRepository {
            return GitRepository(repo, owner)
        }
    }
}
