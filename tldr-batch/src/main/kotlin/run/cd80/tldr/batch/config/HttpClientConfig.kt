package run.cd80.tldr.batch.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import run.cd80.tldr.lib.http.HttpClient
import run.cd80.tldr.lib.http.impl.FuelHttpClient

@Configuration
class HttpClientConfig {

    @Bean
    fun httpClientFactory(): HttpClient =
        FuelHttpClient()
}
