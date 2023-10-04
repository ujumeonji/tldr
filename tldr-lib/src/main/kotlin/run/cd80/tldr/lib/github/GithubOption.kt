package run.cd80.tldr.lib.github

data class GithubOption(
    val clientId: String,
    val clientSecret: String,
    val redirectUri: String,
    val scopes: List<String>,
)
