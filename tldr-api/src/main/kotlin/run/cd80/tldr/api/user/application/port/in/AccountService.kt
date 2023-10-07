package run.cd80.tldr.api.user.application.port.`in`

import run.cd80.tldr.api.domain.user.Account
import run.cd80.tldr.api.user.application.port.`in`.dto.CreateAccount

interface AccountService {

    fun createAccount(command: CreateAccount.Command): Account

    fun findByEmail(email: String): Account?
}
