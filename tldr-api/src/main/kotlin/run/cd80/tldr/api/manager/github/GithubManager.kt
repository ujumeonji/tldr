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

    suspend fun createBlob(
        command: CreateBlob.Command,
        repository: GitRepository,
        accessToken: GithubAccessToken,
    ): CreateBlobResponse {
        val response = httpClientFactory
            .create()
            .post("https://api.github.com/repos/${repository.getFullName()}/git/blobs")
            .header("Accept", "application/vnd.github.v3+json")
            .header("Content-Type", "application/json; charset=utf-8")
            .header("Authorization", "token $accessToken")
            .body(
                mapOf(
                    "content" to command.base64Encode(),
                    "encoding" to "base64",
                ),
            )
            .execute()

        return Gson().fromJson(response.body, CreateBlobResponse::class.java)
    }

    suspend fun getReference(
        branch: String,
        repository: GitRepository,
        accessToken: GithubAccessToken,
    ): GetReferenceResponse {
        val response = httpClientFactory
            .create()
            .get("https://api.github.com/repos/${repository.getFullName()}/git/ref/heads/$branch")
            .header("Accept", "application/vnd.github.v3+json")
            .header("Content-Type", "application/json; charset=utf-8")
            .header("Authorization", "token $accessToken")
            .execute()

        return Gson().fromJson(response.body, GetReferenceResponse::class.java)
    }

    companion object {

        private const val GITHUB_ACCESS_TOKEN_URL = "https://github.com/login/oauth/access_token"

        private val ACCESS_TOKEN_REGEX = """access_token=([\w-]+)""".toRegex()
    }
}
