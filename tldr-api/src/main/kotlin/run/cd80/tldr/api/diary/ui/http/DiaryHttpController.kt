package run.cd80.tldr.api.diary.ui.http

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import run.cd80.tldr.api.diary.ui.http.dto.DailyCalendar
import run.cd80.tldr.api.diary.ui.http.dto.RecentlyViewed
import run.cd80.tldr.api.diary.workflow.GetDiaryCalendarWorkflow
import run.cd80.tldr.api.diary.workflow.GetRecentlyViewedPostWorkflow
import run.cd80.tldr.api.diary.workflow.dto.GetDiaryCalendar
import run.cd80.tldr.api.diary.workflow.dto.GetRecentlyViewed
import run.cd80.tldr.api.domain.auth.DefaultSignInUser
import run.cd80.tldr.lib.calendar.Calendar

@RequestMapping("/diaries")
@RestController
class DiaryHttpController(
    private val getDiaryCalendarWorkflow: GetDiaryCalendarWorkflow,
    private val getRecentlyViewedPostWorkflow: GetRecentlyViewedPostWorkflow,
    private val calendar: Calendar,
) {

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/calendar")
    fun getDiaries(
        dailyCalendar: DailyCalendar.Request,
        @AuthenticationPrincipal authentication: DefaultSignInUser,
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

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/recently-viewed")
    fun getRecentViews(
        recentViewed: RecentlyViewed.Request,
        @AuthenticationPrincipal authentication: OAuth2AuthenticatedPrincipal,
    ): RecentlyViewed.Response {
        val response = getRecentlyViewedPostWorkflow.execute(
            GetRecentlyViewed.Request(
                1L,
                5,
            ),
        )

        return RecentlyViewed.Response(
            response.items.map {
                RecentlyViewed.Response.Diary(it.id, it.title, it.content, it.createdDate)
            },
        )
    }
}
