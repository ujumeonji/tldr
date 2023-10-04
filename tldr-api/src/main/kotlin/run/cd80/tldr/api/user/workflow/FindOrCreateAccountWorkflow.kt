package run.cd80.tldr.api.user.workflow

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import run.cd80.tldr.api.base.WorkflowScenario
import run.cd80.tldr.api.user.application.dto.CreateAccount
import run.cd80.tldr.api.user.application.AccountDomainService
import run.cd80.tldr.api.user.workflow.dto.FindOrCreateAccount

@Component
class FindOrCreateAccountWorkflow(
    private val accountDomainService: AccountDomainService,
) : WorkflowScenario<FindOrCreateAccount.Request, FindOrCreateAccount.Response>() {

    @Transactional
    override fun execute(command: FindOrCreateAccount.Request): FindOrCreateAccount.Response =
        findByEmail(command.email) ?: createAccount(command.email, command.identifier)

    private fun findByEmail(email: String) =
        accountDomainService
            .findByEmail(email)
            ?.let { FindOrCreateAccount.Response(it.email) }

    private fun createAccount(email: String, identifier: String) =
        accountDomainService
            .createAccount(
                CreateAccount.Command(
                    email,
                    identifier,
                )
            )
            .let { FindOrCreateAccount.Response(it.email) }
}
