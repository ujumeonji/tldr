package run.cd80.tldr.api.credential.workflow

import jakarta.transaction.Transactional
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service
import run.cd80.tldr.api.base.WorkflowScenario
import run.cd80.tldr.api.config.GithubConfig
import run.cd80.tldr.api.credential.application.port.inner.CredentialService
import run.cd80.tldr.api.credential.application.port.inner.dto.GithubRegistryCommand
import run.cd80.tldr.api.credential.workflow.dto.CreateGithubCredential
import run.cd80.tldr.api.user.application.port.inner.AccountService
import run.cd80.tldr.lib.github.GithubManager
import run.cd80.tldr.lib.github.GithubOption
import run.cd80.tldr.lib.github.vo.GithubCode
import run.cd80.tldr.lib.http.HttpClient

@Service
class CreateGithubCredentialWorkflow(
    private val accountService: AccountService,
    private val credentialService: CredentialService,
    httpClient: HttpClient,
    githubConfig: GithubConfig,
) : WorkflowScenario<CreateGithubCredential.Request, CreateGithubCredential.Response>() {

    private val githubManager = GithubManager(
        httpClient,
        GithubOption(githubConfig.clientId, githubConfig.clientSecret, githubConfig.redirectUri, githubConfig.scopes),
    )

    @Transactional
    override fun execute(command: CreateGithubCredential.Request): CreateGithubCredential.Response {
        val account =
            accountService.findByUsername(command.username) ?: throw IllegalArgumentException("account not found")
        val accessToken = getAccessToken(command.code)
        val credential = credentialService.register(GithubRegistryCommand(account, "$accessToken"))

        return CreateGithubCredential.Response(
            id = credential.id,
        )
    }

    private fun getAccessToken(code: String) = runBlocking {
        githubManager.getAccessToken(GithubCode.of(code))
    }
}
