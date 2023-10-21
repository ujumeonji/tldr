package run.cd80.tldr.batch.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import run.cd80.tldr.lib.calendar.impl.StandardCalendar

@Configuration
class CalendarConfig {

    @Bean
    fun calendar() = StandardCalendar()
}
