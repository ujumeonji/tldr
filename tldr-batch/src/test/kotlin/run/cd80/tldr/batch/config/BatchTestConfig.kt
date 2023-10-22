package run.cd80.tldr.batch.config

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import run.cd80.tldr.lib.calendar.Calendar
import run.cd80.tldr.lib.http.HttpClient
import run.cd80.tldr.lib.http.impl.FuelHttpClient
import java.time.LocalDateTime

@TestConfiguration
@EnableAutoConfiguration
class BatchTestConfig {

    @Bean
    fun testCalendar(): Calendar =
        object : Calendar {
            override fun now(): LocalDateTime = LocalDateTime.now()

            override fun parse(date: String, pattern: String): LocalDateTime =
                LocalDateTime.now()
        }

    @Bean
    fun testGithubClientConfig(): GithubClientConfig {
        val config = GithubClientConfig()
        config.clientId = "test-client-id"
        config.clientSecret = "test-client-secret"
        config.redirectUri = "test-redirect-uri"
        config.scopes = listOf("test-scope")

        return config
    }

    @Bean
    fun testHttpClientConfig(): HttpClient = FuelHttpClient()
}
