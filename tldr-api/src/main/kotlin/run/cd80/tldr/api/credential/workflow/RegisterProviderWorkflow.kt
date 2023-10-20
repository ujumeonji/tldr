package run.cd80.tldr.api.credential.workflow

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import run.cd80.tldr.api.base.WorkflowScenario
import run.cd80.tldr.api.credential.application.port.inner.CredentialService
import run.cd80.tldr.api.credential.workflow.dto.RegisterProvider
import run.cd80.tldr.api.user.application.port.inner.AccountService

@Service
class RegisterProviderWorkflow(
    private val accountService: AccountService,
    private val credentialService: CredentialService,
) : WorkflowScenario<RegisterProvider.Request, RegisterProvider.Response>() {

    @Transactional
    override fun execute(command: RegisterProvider.Request): RegisterProvider.Response {
        val account =
            accountService.findByUsername(command.username) ?: throw IllegalArgumentException("account not found")

        val credential = credentialService.register(command.toCommand(account))

        return RegisterProvider.Response(
            id = credential.id,
        )
    }
}
