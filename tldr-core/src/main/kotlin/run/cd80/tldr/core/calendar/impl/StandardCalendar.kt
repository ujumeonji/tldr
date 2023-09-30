package run.cd80.tldr.core.calendar.impl

import run.cd80.tldr.core.calendar.Calendar
import java.time.LocalDateTime

class StandardCalendar : Calendar {

    override fun now(): LocalDateTime =
        LocalDateTime.now()
}
