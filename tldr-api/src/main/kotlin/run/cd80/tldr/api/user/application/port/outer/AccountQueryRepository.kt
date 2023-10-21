package run.cd80.tldr.api.user.application.port.outer

import run.cd80.tldr.domain.user.Account

interface AccountQueryRepository {

    fun findByEmail(email: String): Account?

    fun findByUsername(username: String): Account?
}
