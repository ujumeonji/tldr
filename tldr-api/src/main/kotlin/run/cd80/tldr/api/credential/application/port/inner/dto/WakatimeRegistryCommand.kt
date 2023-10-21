package run.cd80.tldr.api.credential.application.port.inner.dto

import run.cd80.tldr.domain.credential.Credential
import run.cd80.tldr.domain.credential.WakaTimeCrendential
import run.cd80.tldr.domain.user.Account

data class WakatimeRegistryCommand(private val account: Account, private val apiKey: String) : RegistryCommand {

    override fun toEntity(): Credential {
        return WakaTimeCrendential.of(account, apiKey)
    }
}
