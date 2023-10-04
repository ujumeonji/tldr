package run.cd80.tldr.api.user.application

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import run.cd80.tldr.api.domain.user.Account
import run.cd80.tldr.api.user.application.dto.CreateAccount
import run.cd80.tldr.api.user.application.port.AccountQueryRepository
import run.cd80.tldr.api.user.application.port.AccountRepository

@Service
@Transactional
class AccountDomainService(
    private val accountRepository: AccountRepository,
    private val accountQueryRepository: AccountQueryRepository,
) {

    fun createAccount(command: CreateAccount.Command): Account =
        Account
            .signUp(command.email, command.identifier)
            .apply(accountRepository::save)

    @Transactional(readOnly = true)
    fun findByEmail(email: String): Account? =
        accountQueryRepository.findByEmail(email)
}
