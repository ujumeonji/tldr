package run.cd80.tldr.api.diary.ui.http

import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import run.cd80.tldr.api.diary.ui.http.dto.DailyCalendar
import run.cd80.tldr.api.diary.workflow.GetDiaryCalendarWorkflow
import run.cd80.tldr.api.diary.workflow.dto.GetDiaryCalendar
import run.cd80.tldr.lib.calendar.Calendar
import java.time.LocalDate

@RequestMapping("/diaries")
@RestController
class DiaryHttpController(
    private val getDiaryCalendarWorkflow: GetDiaryCalendarWorkflow,
    private val calendar: Calendar,
) {

    @GetMapping("/calendar")
    fun getDiaries(
        dailyCalendar: DailyCalendar.Request,
        @AuthenticationPrincipal authentication: OAuth2AuthenticatedPrincipal,
    ): DailyCalendar.Response {
        val now = calendar.now()
        val response = getDiaryCalendarWorkflow.execute(
            GetDiaryCalendar.Request(
                1L,
                now,
            ),
        )

        return DailyCalendar.Response(
            now,
            response.items.map {
                DailyCalendar.Response.Diary(it.id, it.title, it.createdDate)
            },
        )
    }
}
