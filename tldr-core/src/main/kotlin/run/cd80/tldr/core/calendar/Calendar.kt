package run.cd80.tldr.core.calendar

import java.time.LocalDateTime

interface Calendar {

    fun now(): LocalDateTime
}
