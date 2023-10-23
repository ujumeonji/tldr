package run.cd80.tldr.api.challenge.port

import org.springframework.stereotype.Service
import run.cd80.tldr.api.challenge.port.inner.ChallengeService
import run.cd80.tldr.api.challenge.port.outer.ChallengeQueryRepository
import run.cd80.tldr.api.challenge.port.outer.ChallengeRepository
import run.cd80.tldr.domain.challenge.BojChallenge
import run.cd80.tldr.domain.user.Account
import run.cd80.tldr.lib.calendar.Calendar

@Service
class ChallengeDomainService(
    private val challengeRepository: ChallengeRepository,
    private val challengeQueryRepository: ChallengeQueryRepository,
    private val calendar: Calendar,
) : ChallengeService {

    override fun findBojByAccount(account: Account): BojChallenge? =
        challengeQueryRepository.findBojByAccount(account)

    override fun createBoj(account: Account, nickname: String): BojChallenge {
        val challenge = BojChallenge.of(account, nickname, calendar.now())
        challengeRepository.save(challenge)

        return challenge
    }
}
