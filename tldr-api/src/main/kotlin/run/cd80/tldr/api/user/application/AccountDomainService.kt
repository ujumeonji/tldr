package run.cd80.tldr.api.user.application

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import run.cd80.tldr.api.domain.user.Account
import run.cd80.tldr.api.user.application.port.inner.AccountService
import run.cd80.tldr.api.user.application.port.inner.dto.CreateAccount
import run.cd80.tldr.api.user.application.port.out.AccountQueryRepository
import run.cd80.tldr.api.user.application.port.out.AccountRepository
import run.cd80.tldr.lib.calendar.Calendar

@Service
@Transactional
class AccountDomainService(
    private val accountRepository: AccountRepository,
    private val accountQueryRepository: AccountQueryRepository,
    private val calendar: Calendar,
) : AccountService {

    override fun createAccount(command: CreateAccount.Command): Account =
        Account
            .signUp(command.email, calendar.now())
            .apply(accountRepository::save)

    @Transactional(readOnly = true)
    override fun findByEmail(email: String): Account? =
        accountQueryRepository.findByEmail(email)

    @Transactional(readOnly = true)
    override fun findByUsername(username: String): Account? =
        accountQueryRepository.findByUsername(username)
}
