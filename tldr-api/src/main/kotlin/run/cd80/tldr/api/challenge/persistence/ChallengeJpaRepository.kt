package run.cd80.tldr.api.challenge.persistence

import org.springframework.data.repository.CrudRepository
import run.cd80.tldr.domain.challenge.Challenge

interface ChallengeJpaRepository : CrudRepository<Challenge, Long>
