package run.cd80.tldr.api.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import run.cd80.tldr.core.http.HttpClient
import run.cd80.tldr.core.http.impl.FuelHttpClient

@Configuration
class ClientConfig {

    @Bean
    fun httpClient(): HttpClient {
        return FuelHttpClient()
    }
}
