package run.cd80.tldr.api.manager.github

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import run.cd80.tldr.api.config.GithubConfig
import run.cd80.tldr.api.manager.github.dto.CreateBlob
import run.cd80.tldr.api.manager.github.dto.CreateCommit
import run.cd80.tldr.api.manager.github.dto.CreateTree
import run.cd80.tldr.api.manager.github.dto.CreateTreeItem
import run.cd80.tldr.api.manager.github.dto.UpdateHead
import run.cd80.tldr.api.manager.github.dto.UploadFile
import run.cd80.tldr.api.manager.github.vo.GitCommit
import run.cd80.tldr.api.manager.github.vo.GitRepository
import run.cd80.tldr.api.manager.github.vo.GitTree
import run.cd80.tldr.api.manager.github.vo.GithubAccessToken
import run.cd80.tldr.api.manager.github.vo.GithubCode
import run.cd80.tldr.core.http.dto.HttpResponse
import run.cd80.tldr.core.http.impl.FuelHttpClientFactory
import run.cd80.tldr.fixture.StubHttpClient
import run.cd80.tldr.fixture.StubHttpClientFactory

class GithubManagerTest : DescribeSpec({

    val httpClient = StubHttpClient()

    val githubManager = GithubManager(
        StubHttpClientFactory(httpClient),
        GithubConfig().apply {
            clientId = "test-client-id"
            clientSecret = "test-client-secret"
            redirectUri = "http://test:8080"
            scopes = listOf("repo")
        },
    )

    beforeTest {
        httpClient.clear()
    }

    describe("getAccessToken") {
        it("access token을 가져온다.") {
            // given
            httpClient.addResponse(
                HttpResponse(
                    200,
                    "access_token=gho_xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx&scope=repo&token_type=bearer",
                ),
            )

            // when
            val result = githubManager.getAccessToken(GithubCode.of("test-code"))

            // then
            result shouldBe GithubAccessToken.of("gho_xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx")
        }
    }

    describe("getReference") {
        it("특정 저장소에 대한 레퍼런스를 가져온다.") {
            // given
            httpClient.addResponse(
                HttpResponse(
                    200,
                    """
                        {
                          "ref": "refs/heads/main",
                          "node_id": "REF_kwDOKa3xU69yZWZzL2hlYWRzL21haW4",
                          "url": "https://api.github.com/repos/dygma0/git-test/git/refs/heads/main",
                          "object": {
                            "sha": "4eb26e0f47059b222a4a44c0cbd6c8b47d1998d4",
                            "type": "commit",
                            "url": "https://api.github.com/repos/dygma0/git-test/git/commits/4eb26e0f47059b222a4a44c0cbd6c8b47d1998d4"
                          }
                        }
                    """.trimIndent(),
                ),
            )

            // when
            val result = githubManager.getReference(
                "test-repository",
                GitRepository.of("test", "repo"),
                GithubAccessToken.of("gho_xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"),
            )

            // then
            result.url shouldBe "https://api.github.com/repos/dygma0/git-test/git/refs/heads/main"
            result.ref shouldBe "refs/heads/main"
            result.nodeId shouldBe "REF_kwDOKa3xU69yZWZzL2hlYWRzL21haW4"
            result.`object`.sha shouldBe "4eb26e0f47059b222a4a44c0cbd6c8b47d1998d4"
            result.`object`.type shouldBe "commit"
            result.`object`.url shouldBe "https://api.github.com/repos/dygma0/git-test/git/commits/4eb26e0f47059b222a4a44c0cbd6c8b47d1998d4"
        }
    }

    describe("createBlob") {
        it("blob 생성") {
            // given
            httpClient.addResponse(
                HttpResponse(
                    200,
                    """
                        {
                          "sha": "ee13cb732d05301c7a514512450b572db10293c8",
                          "url": "https://api.github.com/repos/dygma0/git-test/git/blobs/ee13cb732d05301c7a514512450b572db10293c8"
                        }
                    """.trimIndent(),
                ),
            )

            // when
            val result = githubManager.createBlob(
                CreateBlob.Command("test-content", "repo/path", "test-encoding"),
                GitRepository.of("test", "repo"),
                GithubAccessToken.of("gho_xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"),
            )

            // then
            result.sha shouldBe "ee13cb732d05301c7a514512450b572db10293c8"
            result.url shouldBe "https://api.github.com/repos/dygma0/git-test/git/blobs/ee13cb732d05301c7a514512450b572db10293c8"
        }
    }

    describe("createBlob") {
        it("blob 생성") {
            // given
            httpClient.addResponse(
                HttpResponse(
                    200,
                    """
                        {
                          "sha": "ee13cb732d05301c7a514512450b572db10293c8",
                          "url": "https://api.github.com/repos/dygma0/git-test/git/blobs/ee13cb732d05301c7a514512450b572db10293c8"
                        }
                    """.trimIndent(),
                ),
            )

            // when
            val result = githubManager.createBlob(
                CreateBlob.Command("test-content", "repo/path", "test-encoding"),
                GitRepository.of("test", "repo"),
                GithubAccessToken.of("gho_xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"),
            )

            // then
            result.sha shouldBe "ee13cb732d05301c7a514512450b572db10293c8"
            result.url shouldBe "https://api.github.com/repos/dygma0/git-test/git/blobs/ee13cb732d05301c7a514512450b572db10293c8"
        }
    }

    describe("createTree") {
        it("tree 정보를 생성한다.") {
            // given
            httpClient.addResponse(
                HttpResponse(
                    200,
                    """
                        {
                          "sha": "208bf63cc35f434c8be9bcfeaf24a7413b564107",
                          "url": "https://api.github.com/repos/dygma0/git-test/git/trees/208bf63cc35f434c8be9bcfeaf24a7413b564107",
                          "tree": [
                            {
                              "path": "README.md",
                              "mode": "100644",
                              "type": "blob",
                              "sha": "2829ba955ca85682eb7ee6b622a440d176c21df5",
                              "size": 11,
                              "url": "https://api.github.com/repos/dygma0/git-test/git/blobs/2829ba955ca85682eb7ee6b622a440d176c21df5"
                            },
                            {
                              "path": "test",
                              "mode": "040000",
                              "type": "tree",
                              "sha": "6556dfa46d469ce8ddb5db087527614028ded375",
                              "url": "https://api.github.com/repos/dygma0/git-test/git/trees/6556dfa46d469ce8ddb5db087527614028ded375"
                            },
                            {
                              "path": "백준",
                              "mode": "040000",
                              "type": "tree",
                              "sha": "385a1b1e60aba9a68e7e1d0fc447290b027bcb26",
                              "url": "https://api.github.com/repos/dygma0/git-test/git/trees/385a1b1e60aba9a68e7e1d0fc447290b027bcb26"
                            }
                          ],
                          "truncated": false
                        }
                    """.trimIndent(),
                ),
            )

            // when
            val result = githubManager.createTree(
                CreateTree.Command(
                    "test-base-tree-sha",
                    listOf(
                        CreateTreeItem.of("test-path", "test-mode", "test-type", "test-sha"),
                    ),
                ),
                GitRepository.of("test", "repo"),
                GithubAccessToken.of("gho_xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"),
            )

            // then
            result.sha shouldBe "208bf63cc35f434c8be9bcfeaf24a7413b564107"
            result.url shouldBe "https://api.github.com/repos/dygma0/git-test/git/trees/208bf63cc35f434c8be9bcfeaf24a7413b564107"
            result.truncated shouldBe false
            result.tree.size shouldBe 3
            result.tree[0].path shouldBe "README.md"
            result.tree[0].mode shouldBe "100644"
            result.tree[0].type shouldBe "blob"
            result.tree[0].sha shouldBe "2829ba955ca85682eb7ee6b622a440d176c21df5"
            result.tree[0].size shouldBe 11
            result.tree[0].url shouldBe "https://api.github.com/repos/dygma0/git-test/git/blobs/2829ba955ca85682eb7ee6b622a440d176c21df5"
            result.tree[1].path shouldBe "test"
            result.tree[1].mode shouldBe "040000"
            result.tree[1].type shouldBe "tree"
            result.tree[1].sha shouldBe "6556dfa46d469ce8ddb5db087527614028ded375"
            result.tree[1].url shouldBe "https://api.github.com/repos/dygma0/git-test/git/trees/6556dfa46d469ce8ddb5db087527614028ded375"
            result.tree[2].path shouldBe "백준"
            result.tree[2].mode shouldBe "040000"
            result.tree[2].type shouldBe "tree"
            result.tree[2].sha shouldBe "385a1b1e60aba9a68e7e1d0fc447290b027bcb26"
            result.tree[2].url shouldBe "https://api.github.com/repos/dygma0/git-test/git/trees/385a1b1e60aba9a68e7e1d0fc447290b027bcb26"
        }
    }

    describe("createCommit") {

        it("commit 정보를 생성한다.") {
            // given
            httpClient.addResponse(
                HttpResponse(
                    200,
                    """
                        {
                          "sha": "4eb26e0f47059b222a4a44c0cbd6c8b47d1998d4",
                          "node_id": "C_kwDOKa3xU9oAKDRlYjI2ZTBmNDcwNTliMjIyYTRhNDRjMGNiZDZjOGI0N2QxOTk4ZDQ",
                          "url": "https://api.github.com/repos/dygma0/git-test/git/commits/4eb26e0f47059b222a4a44c0cbd6c8b47d1998d4",
                          "html_url": "https://github.com/dygma0/git-test/commit/4eb26e0f47059b222a4a44c0cbd6c8b47d1998d4",
                          "author": {
                            "name": "dunno",
                            "email": "72547111+dygma0@users.noreply.github.com",
                            "date": "2023-10-02T09:38:23Z"
                          },
                          "committer": {
                            "name": "dunno",
                            "email": "72547111+dygma0@users.noreply.github.com",
                            "date": "2023-10-02T09:38:23Z"
                          },
                          "tree": {
                            "sha": "7f967255cf28971fea9682b37b8e313338521c75",
                            "url": "https://api.github.com/repos/dygma0/git-test/git/trees/7f967255cf28971fea9682b37b8e313338521c75"
                          },
                          "message": "[Bronze V] Title: A-B, Time: 0 ms, Memory: 13024 KB -BaekjoonHub",
                          "parents": [
                            {
                              "sha": "66123797e35e9c10e7e7de91a15105a6c74c50c1",
                              "url": "https://api.github.com/repos/dygma0/git-test/git/commits/66123797e35e9c10e7e7de91a15105a6c74c50c1",
                              "html_url": "https://github.com/dygma0/git-test/commit/66123797e35e9c10e7e7de91a15105a6c74c50c1"
                            }
                          ],
                          "verification": {
                            "verified": false,
                            "reason": "unsigned",
                            "signature": null,
                            "payload": null
                          }
                        }
                    """.trimIndent(),
                ),
            )

            // when
            val result = githubManager.createCommit(
                CreateCommit.Command(
                    "test-message",
                    GitTree.SHA("test-tree"),
                    listOf("test-parent"),
                ),
                GitRepository.of("test", "repo"),
                GithubAccessToken.of("gho_xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"),
            )

            // then
            result.sha shouldBe "4eb26e0f47059b222a4a44c0cbd6c8b47d1998d4"
            result.url shouldBe "https://api.github.com/repos/dygma0/git-test/git/commits/4eb26e0f47059b222a4a44c0cbd6c8b47d1998d4"
            result.htmlUrl shouldBe "https://github.com/dygma0/git-test/commit/4eb26e0f47059b222a4a44c0cbd6c8b47d1998d4"
            result.author.name shouldBe "dunno"
            result.author.email shouldBe "72547111+dygma0@users.noreply.github.com"
            result.author.date shouldBe "2023-10-02T09:38:23Z"
            result.committer.name shouldBe "dunno"
            result.committer.email shouldBe "72547111+dygma0@users.noreply.github.com"
            result.committer.date shouldBe "2023-10-02T09:38:23Z"
            result.tree.sha shouldBe "7f967255cf28971fea9682b37b8e313338521c75"
            result.tree.url shouldBe "https://api.github.com/repos/dygma0/git-test/git/trees/7f967255cf28971fea9682b37b8e313338521c75"
            result.message shouldBe "[Bronze V] Title: A-B, Time: 0 ms, Memory: 13024 KB -BaekjoonHub"
            result.parents.size shouldBe 1
            result.parents[0].sha shouldBe "66123797e35e9c10e7e7de91a15105a6c74c50c1"
            result.parents[0].url shouldBe "https://api.github.com/repos/dygma0/git-test/git/commits/66123797e35e9c10e7e7de91a15105a6c74c50c1"
            result.parents[0].htmlUrl shouldBe "https://github.com/dygma0/git-test/commit/66123797e35e9c10e7e7de91a15105a6c74c50c1"
            result.verification.verified shouldBe false
            result.verification.reason shouldBe "unsigned"
            result.verification.signature shouldBe null
            result.verification.payload shouldBe null
        }
    }

    describe("updateHead") {

        it("haed 정보를 갱신한다.") {
            // given
            httpClient.addResponse(
                HttpResponse(
                    200,
                    """
                        {
                          "ref": "refs/heads/main",
                          "url": "https://api.github.com/repos/dygma0/git-test/git/refs/heads/main",
                          "object": {
                            "sha": "4eb26e0f47059b222a4a44c0cbd6c8b47d1998d4",
                            "type": "commit",
                            "url": "https://api.github.com/repos/dygma0/git-test/git/commits/4eb26e0f47059b222a4a44c0cbd6c8b47d1998d4"
                          }
                        }
                    """.trimIndent(),
                ),
            )

            // when
            val result = githubManager.updateHead(
                UpdateHead.Command(
                    "test-branch",
                    GitCommit.SHA("test-sha"),
                    true,
                ),
                GitRepository.of("test", "repo"),
                GithubAccessToken.of("gho_xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"),
            )

            // then
            result.ref shouldBe "refs/heads/main"
            result.url shouldBe "https://api.github.com/repos/dygma0/git-test/git/refs/heads/main"
            result.`object`.sha shouldBe "4eb26e0f47059b222a4a44c0cbd6c8b47d1998d4"
            result.`object`.type shouldBe "commit"
            result.`object`.url shouldBe "https://api.github.com/repos/dygma0/git-test/git/commits/4eb26e0f47059b222a4a44c0cbd6c8b47d1998d4"
        }
    }

    xdescribe("FuelHttpClient") {
        it("integration test") {
            val fuelHttpClientFactory = FuelHttpClientFactory()
            val testClient = GithubManager(
                fuelHttpClientFactory,
                GithubConfig().apply {
                    clientId = "test-client-id"
                    clientSecret = "test-client-secret"
                    redirectUri = "test-redirect-uri"
                    scopes = listOf("repo")
                },
            )

            testClient.uploadFile(
                UploadFile.Command(
                    "커밋 테스트",
                    "Hello, World!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!",
                    "main",
                    "test/README.md",
                ),
                GitRepository.of("test", "git-test"),
                GithubAccessToken.of("gho_xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"),
            );
        }
    }
})
