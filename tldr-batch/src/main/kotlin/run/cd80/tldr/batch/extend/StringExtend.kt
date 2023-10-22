package run.cd80.tldr.batch.extend

import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun String.toDate(): LocalDate =
    LocalDate.parse(this, LocalDateExtend.FORMAT)

object LocalDateExtend {

        val FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd")
}
