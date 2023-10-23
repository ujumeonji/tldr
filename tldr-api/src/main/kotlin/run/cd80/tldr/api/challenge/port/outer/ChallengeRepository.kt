package run.cd80.tldr.api.challenge.port.outer

import run.cd80.tldr.domain.challenge.Challenge

interface ChallengeRepository {

    fun save(challenge: Challenge): Challenge
}
