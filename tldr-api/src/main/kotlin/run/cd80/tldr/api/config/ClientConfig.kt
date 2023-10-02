package run.cd80.tldr.api.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import run.cd80.tldr.core.calendar.Calendar
import run.cd80.tldr.core.calendar.impl.StandardCalendar
import run.cd80.tldr.core.http.HttpClientFactory
import run.cd80.tldr.core.http.impl.FuelHttpClientFactory

@Configuration
class ClientConfig {

    @Bean
    fun httpClientFactory(): HttpClientFactory =
        FuelHttpClientFactory()

    @Bean
    fun calendar(): Calendar {
        return StandardCalendar()
    }
}
