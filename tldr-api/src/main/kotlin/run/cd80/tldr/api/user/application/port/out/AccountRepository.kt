package run.cd80.tldr.api.user.application.port.out

import run.cd80.tldr.domain.user.Account

interface AccountRepository {

    fun save(account: Account)
}
