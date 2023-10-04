package run.cd80.tldr.api.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import run.cd80.tldr.lib.calendar.Calendar
import run.cd80.tldr.lib.calendar.impl.StandardCalendar

@Configuration
class CommonConfig {

    @Bean
    fun calendar(): Calendar {
        return StandardCalendar()
    }
}
