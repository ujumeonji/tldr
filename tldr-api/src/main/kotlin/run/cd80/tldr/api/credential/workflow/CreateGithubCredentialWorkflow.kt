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
import run.cd80.tldr.lib.calendar.Calendar
import run.cd80.tldr.lib.github.GithubManager
import run.cd80.tldr.lib.github.GithubOption
import run.cd80.tldr.lib.github.vo.GithubCode
import run.cd80.tldr.lib.http.HttpClient

@Service
class CreateGithubCredentialWorkflow(
    private val accountService: AccountService,
    private val credentialService: CredentialService,
    private val calendar: Calendar,
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

        val credential = credentialService.findGithubByAccount(account)
        val accessToken = getAccessToken(command.code)

        if (credential == null) {
            val newCredential =
                credentialService.register(GithubRegistryCommand(account, "$accessToken", calendar.now()))
            return CreateGithubCredential.Response(
                id = newCredential.id,
            )
        }

        credential.updateCredential("$accessToken")
        return CreateGithubCredential.Response(
            id = credential.id,
        )
    }

    private fun getAccessToken(code: String) = runBlocking {
        githubManager.getAccessToken(GithubCode.of(code))
    }
}

// https://github.com/login/oauth/authorize?client_id=f343bf99a9675ea232cb&redirect_uri=http://localhost:8080/api/credentials/github/callback&scope=repo
