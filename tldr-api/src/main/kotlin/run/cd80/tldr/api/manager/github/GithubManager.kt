package run.cd80.tldr.api.manager.github

import org.springframework.stereotype.Component
import run.cd80.tldr.api.config.GithubConfig
import run.cd80.tldr.api.manager.github.vo.GithubAccessToken
import run.cd80.tldr.api.manager.github.vo.GithubCode
import run.cd80.tldr.core.http.HttpClientFactory

@Component
class GithubManager(
    private val httpClientFactory: HttpClientFactory,
    private val githubConfig: GithubConfig,
) {

    suspend fun getAccessToken(code: GithubCode): GithubAccessToken {
        val response = httpClientFactory
            .create()
            .post(GITHUB_ACCESS_TOKEN_URL)
            .header("Content-Type", "application/json; charset=utf-8")
            .body(
                mapOf(
                    "client_id" to githubConfig.clientId,
                    "client_secret" to githubConfig.clientSecret,
                    "code" to code,
                ),
            )
            .execute()

        return ACCESS_TOKEN_REGEX
            .find(response.body)
            ?.groupValues?.get(1)
            ?.let(GithubAccessToken::of)
            ?: GithubAccessToken.EMPTY
    }

    companion object {

        private const val GITHUB_ACCESS_TOKEN_URL = "https://github.com/login/oauth/access_token"

        private val ACCESS_TOKEN_REGEX = """access_token=([\w-]+)""".toRegex()
    }
}
