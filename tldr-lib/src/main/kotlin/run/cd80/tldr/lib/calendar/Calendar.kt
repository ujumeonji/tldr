package run.cd80.tldr.lib.calendar

import java.time.LocalDateTime

interface Calendar {

    fun now(): LocalDateTime

    fun parse(date: String, pattern: String): LocalDateTime
}
