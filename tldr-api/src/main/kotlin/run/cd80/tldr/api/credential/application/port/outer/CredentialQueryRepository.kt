package run.cd80.tldr.api.credential.application.port.outer

import run.cd80.tldr.domain.credential.GithubCredential
import run.cd80.tldr.domain.user.Account

interface CredentialQueryRepository {

    fun findGithubByAccount(account: Account): GithubCredential?
}
