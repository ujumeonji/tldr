package run.cd80.tldr.api.manager.github

import com.google.gson.Gson
import org.springframework.stereotype.Component
import run.cd80.tldr.api.config.GithubConfig
import run.cd80.tldr.api.manager.github.dto.CreateBlob
import run.cd80.tldr.api.manager.github.dto.CreateCommit
import run.cd80.tldr.api.manager.github.dto.CreateTree
import run.cd80.tldr.api.manager.github.dto.CreateTreeItem
import run.cd80.tldr.api.manager.github.dto.UpdateHead
import run.cd80.tldr.api.manager.github.response.CreateBlobResponse
import run.cd80.tldr.api.manager.github.response.CreateCommitResponse
import run.cd80.tldr.api.manager.github.response.CreateTreeResponse
import run.cd80.tldr.api.manager.github.response.GetReferenceResponse
import run.cd80.tldr.api.manager.github.response.UpdateHeadResponse
import run.cd80.tldr.api.manager.github.vo.GitRepository
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

    suspend fun updateHead(
        command: UpdateHead.Command,
        repository: GitRepository,
        accessToken: GithubAccessToken,
    ): UpdateHeadResponse {
        val response = httpClientFactory
            .create()
            .patch("https://api.github.com/repos/${repository.getFullName()}/git/refs/heads/${command.branch}")
            .header("Accept", "application/vnd.github.v3+json")
            .header("Content-Type", "application/json; charset=utf-8")
            .header("Authorization", "Bearer $accessToken")
            .body(
                mapOf(
                    "sha" to command.sha,
                    "force" to command.force,
                ),
            )
            .execute()

        return Gson().fromJson(response.body, UpdateHeadResponse::class.java)
    }

    suspend fun createCommit(
        command: CreateCommit.Command,
        repository: GitRepository,
        accessToken: GithubAccessToken,
    ): CreateCommitResponse {
        val response = httpClientFactory
            .create()
            .post("https://api.github.com/repos/${repository.getFullName()}/git/commits")
            .header("Accept", "application/vnd.github.v3+json")
            .header("Content-Type", "application/json; charset=utf-8")
            .header("Authorization", "Bearer $accessToken")
            .body(
                mapOf(
                    "message" to command.message,
                    "tree" to command.tree,
                    "parents" to command.parents,
                ),
            )
            .execute()

        return Gson().fromJson(response.body, CreateCommitResponse::class.java)
    }

    suspend fun createTree(
        command: CreateTree.Command,
        repository: GitRepository,
        accessToken: GithubAccessToken,
    ): CreateTreeResponse {
        val response = httpClientFactory
            .create()
            .post("https://api.github.com/repos/${repository.getFullName()}/git/trees")
            .header("Accept", "application/vnd.github.v3+json")
            .header("Content-Type", "application/json; charset=utf-8")
            .header("Authorization", "Bearer $accessToken")
            .body(
                mapOf(
                    "base_tree" to command.baseTreeSHA,
                    "tree" to command.trees.map(CreateTreeItem::toMap),
                ),
            )
            .execute()

        return Gson().fromJson(response.body, CreateTreeResponse::class.java)
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
            .header("Authorization", "Bearer $accessToken")
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
            .header("Authorization", "Bearer $accessToken")
            .execute()

        return Gson().fromJson(response.body, GetReferenceResponse::class.java)
    }

    companion object {

        private const val GITHUB_ACCESS_TOKEN_URL = "https://github.com/login/oauth/access_token"

        private val ACCESS_TOKEN_REGEX = """access_token=([\w-]+)""".toRegex()
    }
}
