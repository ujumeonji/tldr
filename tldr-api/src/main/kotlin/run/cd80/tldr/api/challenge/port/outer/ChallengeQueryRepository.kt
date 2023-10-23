package run.cd80.tldr.api.challenge.port.outer

import run.cd80.tldr.domain.challenge.BojChallenge
import run.cd80.tldr.domain.user.Account

interface ChallengeQueryRepository {

    fun findBojByAccount(account: Account): BojChallenge?
}
