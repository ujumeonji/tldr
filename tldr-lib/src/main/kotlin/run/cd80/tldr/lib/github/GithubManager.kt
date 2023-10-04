package run.cd80.tldr.lib.github

import com.google.gson.Gson
import run.cd80.tldr.lib.github.dto.CreateBlob
import run.cd80.tldr.lib.github.dto.CreateCommit
import run.cd80.tldr.lib.github.dto.CreateTree
import run.cd80.tldr.lib.github.dto.CreateTreeItem
import run.cd80.tldr.lib.github.dto.UpdateHead
import run.cd80.tldr.lib.github.dto.UploadFile
import run.cd80.tldr.lib.github.response.CreateBlobResponse
import run.cd80.tldr.lib.github.response.CreateCommitResponse
import run.cd80.tldr.lib.github.response.CreateTreeResponse
import run.cd80.tldr.lib.github.response.GetReferenceResponse
import run.cd80.tldr.lib.github.response.UpdateHeadResponse
import run.cd80.tldr.lib.github.vo.GitBlob
import run.cd80.tldr.lib.github.vo.GitCommit
import run.cd80.tldr.lib.github.vo.GitRepository
import run.cd80.tldr.lib.github.vo.GitTree
import run.cd80.tldr.lib.github.vo.GithubAccessToken
import run.cd80.tldr.lib.github.vo.GithubCode
import run.cd80.tldr.lib.http.HttpClient

class GithubManager(
    private val httpClient: HttpClient,
    private val option: GithubOption,
) {

    suspend fun uploadFile(command: UploadFile.Command, repository: GitRepository, accessToken: GithubAccessToken) {
        val reference = getReference(command.branch, repository, accessToken)
        val blob = createBlob(
            CreateBlob.Command(
                content = command.content.contentData(),
                path = command.path,
                encoding = command.content.encodeType(),
            ),
            repository,
            accessToken,
        )
        val tree = createTree(
            CreateTree.Command(
                baseTree = reference.`object`.sha,
                trees = listOf(
                    CreateTreeItem(
                        sha = GitBlob.SHA(blob.sha),
                        path = command.path,
                    ),
                ),
            ),
            repository,
            accessToken,
        )
        val commit = createCommit(
            CreateCommit.Command(
                message = command.message,
                tree = GitTree.SHA(tree.sha),
                parents = listOf(reference.`object`.sha),
            ),
            repository,
            accessToken,
        )
        updateHead(
            UpdateHead.Command(
                branch = command.branch,
                sha = GitCommit.SHA(commit.sha),
                force = true,
            ),
            repository,
            accessToken,
        )
    }

    suspend fun getAccessToken(code: GithubCode): GithubAccessToken {
        val response = httpClient.post(run.cd80.tldr.lib.github.GithubManager.Companion.GITHUB_ACCESS_TOKEN_URL) {
            header("Content-Type", "application/json; charset=utf-8")
            body(
                mapOf(
                    "client_id" to option.clientId,
                    "client_secret" to option.clientSecret,
                    "code" to code,
                ),
            )
        }

        return run.cd80.tldr.lib.github.GithubManager.Companion.ACCESS_TOKEN_REGEX
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
        val response =
            httpClient.patch("https://api.github.com/repos/${repository.getFullName()}/git/refs/heads/${command.branch}") {
                header("Accept", "application/vnd.github.v3+json")
                header("Content-Type", "application/json; charset=utf-8")
                header("Authorization", "Bearer $accessToken")
                body(
                    mapOf(
                        "sha" to command.sha.toString(),
                        "force" to command.force,
                    ),
                )
            }

        return Gson().fromJson(response.body, UpdateHeadResponse::class.java)
    }

    suspend fun createCommit(
        command: CreateCommit.Command,
        repository: GitRepository,
        accessToken: GithubAccessToken,
    ): CreateCommitResponse {
        val response = httpClient
            .post("https://api.github.com/repos/${repository.getFullName()}/git/commits") {
                header("Accept", "application/vnd.github.v3+json")
                header("Content-Type", "application/json; charset=utf-8")
                header("Authorization", "Bearer $accessToken")
                body(
                    mapOf(
                        "message" to command.message,
                        "tree" to command.tree.toString(),
                        "parents" to command.parents,
                    ),
                )
            }

        return Gson().fromJson(response.body, CreateCommitResponse::class.java)
    }

    suspend fun createTree(
        command: CreateTree.Command,
        repository: GitRepository,
        accessToken: GithubAccessToken,
    ): CreateTreeResponse {
        val response = httpClient.post("https://api.github.com/repos/${repository.getFullName()}/git/trees") {
            header("Accept", "application/vnd.github.v3+json")
            header("Content-Type", "application/json; charset=utf-8")
            header("Authorization", "Bearer $accessToken")
            body(
                mapOf(
                    "base_tree" to command.baseTree,
                    "tree" to command.trees.map(CreateTreeItem::toMap),
                ),
            )
        }

        return Gson().fromJson(response.body, CreateTreeResponse::class.java)
    }

    suspend fun createBlob(
        command: CreateBlob.Command,
        repository: GitRepository,
        accessToken: GithubAccessToken,
    ): CreateBlobResponse {
        val response = httpClient
            .post("https://api.github.com/repos/${repository.getFullName()}/git/blobs") {
                header("Accept", "application/vnd.github.v3+json")
                header("Content-Type", "application/json; charset=utf-8")
                header("Authorization", "Bearer $accessToken")
                body(
                    mapOf(
                        "content" to command.content,
                        "encoding" to command.encoding.value,
                    ),
                )
            }

        return Gson().fromJson(response.body, CreateBlobResponse::class.java)
    }

    suspend fun getReference(
        branch: String,
        repository: GitRepository,
        accessToken: GithubAccessToken,
    ): GetReferenceResponse {
        val response = httpClient
            .get("https://api.github.com/repos/${repository.getFullName()}/git/ref/heads/$branch") {
                header("Accept", "application/vnd.github.v3+json")
                header("Content-Type", "application/json; charset=utf-8")
                header("Authorization", "Bearer $accessToken")
            }

        return Gson().fromJson(response.body, GetReferenceResponse::class.java)
    }

    companion object {

        private const val GITHUB_ACCESS_TOKEN_URL = "https://github.com/login/oauth/access_token"

        private val ACCESS_TOKEN_REGEX = """access_token=([\w-]+)""".toRegex()
    }
}
