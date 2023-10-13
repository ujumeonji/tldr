package run.cd80.tldr.api.user.application.port.inner

import run.cd80.tldr.api.domain.user.Account
import run.cd80.tldr.api.user.application.port.inner.dto.CreateAccount

interface AccountService {

    fun createAccount(command: CreateAccount.Command): Account

    fun findByEmail(email: String): Account?

    fun findByUsername(username: String): Account?
}
