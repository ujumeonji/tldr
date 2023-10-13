package run.cd80.tldr.api.user.application.port.out

import run.cd80.tldr.api.domain.user.Account

interface AccountQueryRepository {

    fun findByEmail(email: String): Account?

    fun findByUsername(username: String): Account?
}
