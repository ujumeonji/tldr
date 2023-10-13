package run.cd80.tldr.lib.calendar.impl

import run.cd80.tldr.lib.calendar.Calendar
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class StandardCalendar : Calendar {

    override fun now(): LocalDateTime =
        LocalDateTime.now()

    override fun parse(date: String, pattern: String): LocalDateTime =
        LocalDateTime.parse(date, DateTimeFormatter.ofPattern(pattern))
}
