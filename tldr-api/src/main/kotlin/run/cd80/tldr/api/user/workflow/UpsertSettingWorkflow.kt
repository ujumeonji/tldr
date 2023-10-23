package run.cd80.tldr.api.user.workflow

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import run.cd80.tldr.api.base.WorkflowScenario
import run.cd80.tldr.api.challenge.port.inner.ChallengeService
import run.cd80.tldr.api.credential.application.port.inner.CredentialService
import run.cd80.tldr.api.user.application.port.inner.AccountService
import run.cd80.tldr.api.user.workflow.dto.UpsertSetting

@Component
class UpsertSettingWorkflow(
    private val accountDomainService: AccountService,
    private val challengeService: ChallengeService,
    private val credentialService: CredentialService,
) : WorkflowScenario<UpsertSetting.Request, Unit>() {

    @Transactional
    override fun execute(command: UpsertSetting.Request) {
        val account = accountDomainService.findByUsername(command.username) ?: return
        val credential = credentialService.findGithubByAccount(account) ?: return
        val challenge =
            challengeService.findBojByAccount(account) ?: challengeService.createBoj(account, command.bojNickname)

        credential.update(command.owner, command.repository)
        challenge.update(command.bojNickname)
    }
}
