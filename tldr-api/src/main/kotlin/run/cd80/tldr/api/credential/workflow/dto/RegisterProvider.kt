package run.cd80.tldr.api.credential.workflow.dto

import run.cd80.tldr.api.credential.application.port.inner.dto.GithubRegistryCommand
import run.cd80.tldr.api.credential.application.port.inner.dto.WakatimeRegistryCommand
import run.cd80.tldr.domain.user.Account
import java.time.LocalDateTime

object RegisterProvider {

    data class Request(
        val username: String,
        private val provider: String,
        private val apiKey: String?,
        private val accessToken: String?,
        private val owner: String?,
        private val repository: String?,
        private val registerAt: LocalDateTime = LocalDateTime.now(),
    ) {

        fun toCommand(account: Account) = when (provider) {
            "github" -> GithubRegistryCommand(account, accessToken!!, registerAt)
            "wakatime" -> WakatimeRegistryCommand(account, apiKey!!)
            else -> throw IllegalArgumentException("Invalid provider: $provider")
        }
    }

    data class Response(
        val id: Long,
    )
}
