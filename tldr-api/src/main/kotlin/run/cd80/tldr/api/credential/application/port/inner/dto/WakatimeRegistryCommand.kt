package run.cd80.tldr.api.credential.application.port.inner.dto

import run.cd80.tldr.api.domain.credential.Credential
import run.cd80.tldr.api.domain.credential.WakaTimeCrendential

data class WakatimeRegistryCommand(private val apiKey: String) : RegistryCommand {

    override fun toEntity(): Credential {
        return WakaTimeCrendential.of(apiKey)
    }
}
