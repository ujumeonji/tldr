package run.cd80.tldr.lib.calendar.impl

import run.cd80.tldr.lib.calendar.Calendar
import java.time.LocalDateTime

class StandardCalendar : Calendar {

    override fun now(): LocalDateTime =
        LocalDateTime.now()
}
