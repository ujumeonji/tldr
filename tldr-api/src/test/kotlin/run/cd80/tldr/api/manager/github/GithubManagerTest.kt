package run.cd80.tldr.api.manager.github

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import run.cd80.tldr.api.config.GithubConfig
import run.cd80.tldr.api.manager.github.vo.GithubAccessToken
import run.cd80.tldr.api.manager.github.vo.GithubCode
import run.cd80.tldr.core.http.dto.HttpResponse
import run.cd80.tldr.fixture.StubHttpClient
import run.cd80.tldr.fixture.StubHttpClientFactory

class GithubManagerTest : BehaviorSpec({

    given("a github manager") {
        val config = GithubConfig()
        config.clientId = "test-client-id"
        config.clientSecret = "test-client-secret"
        config.redirectUri = "http://test:8080"
        config.scopes = listOf("repo")

        val httpClient = StubHttpClient()
        httpClient.addResponse(
            HttpResponse(
                200,
                "access_token=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&scope=repo&token_type=bearer",
            ),
        )

        val githubManager = GithubManager(
            StubHttpClientFactory(httpClient),
            config,
        )

        `when`("getDurations를 호출한 경우") {
            val result = githubManager.getAccessToken(GithubCode.of("test-code"))

            then("return access token") {
                result shouldBe GithubAccessToken.of("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")
            }
        }
    }
})
