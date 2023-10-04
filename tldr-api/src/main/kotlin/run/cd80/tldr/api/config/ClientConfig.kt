package run.cd80.tldr.api.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import run.cd80.tldr.lib.http.HttpClient
import run.cd80.tldr.lib.http.impl.FuelHttpClient

@Configuration
class ClientConfig {

    @Bean
    fun httpClientFactory(): HttpClient =
        FuelHttpClient()
}
