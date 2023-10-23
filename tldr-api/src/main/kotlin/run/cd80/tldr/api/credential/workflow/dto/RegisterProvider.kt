package run.cd80.tldr.api.credential.workflow.dto

import run.cd80.tldr.api.challenge.port.inner.dto.GithubRegistryCommand
import run.cd80.tldr.api.challenge.port.inner.dto.WakatimeRegistryCommand
import run.cd80.tldr.domain.user.Account

object RegisterProvider {

    data class Request(
        val username: String,
        private val provider: String,
        private val apiKey: String?,
        private val accessToken: String?,
        private val owner: String?,
        private val repository: String?,
    ) {

        fun toCommand(account: Account) = when (provider) {
            "github" -> GithubRegistryCommand(account, accessToken!!)
            "wakatime" -> WakatimeRegistryCommand(account, apiKey!!)
            else -> throw IllegalArgumentException("Invalid provider: $provider")
        }
    }

    data class Response(
        val id: Long,
    )
}
