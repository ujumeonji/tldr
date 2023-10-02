package run.cd80.tldr.api.manager.github.vo

data class GitRepository(private val name: String, private val owner: String) {

    fun getFullName(): String {
        return "$owner/$name"
    }

    companion object {

        fun of(fullName: String): GitRepository {
            val split = fullName.split("/")
            return GitRepository(split[1], split[0])
        }
    }
}
