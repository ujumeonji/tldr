package run.cd80.tldr.api.challenge.port.inner

import run.cd80.tldr.domain.challenge.BojChallenge
import run.cd80.tldr.domain.user.Account

interface ChallengeService {

    fun findBojByAccount(account: Account): BojChallenge?

    fun createBoj(account: Account, nickname: String): BojChallenge
}
