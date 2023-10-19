package run.cd80.tldr.api.diary.ui.http

import jakarta.validation.Valid
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import run.cd80.tldr.api.diary.ui.http.dto.CreateDiaryDto
import run.cd80.tldr.api.diary.ui.http.dto.DailyCalendar
import run.cd80.tldr.api.diary.ui.http.dto.GetDiaryBySlugDto
import run.cd80.tldr.api.diary.ui.http.dto.RecentlyViewed
import run.cd80.tldr.api.diary.workflow.CreateDiaryWorkflow
import run.cd80.tldr.api.diary.workflow.GetDiaryBySlugWorkflow
import run.cd80.tldr.api.diary.workflow.GetDiaryCalendarWorkflow
import run.cd80.tldr.api.diary.workflow.GetRecentlyViewedPostWorkflow
import run.cd80.tldr.api.diary.workflow.dto.CreateDiary
import run.cd80.tldr.api.diary.workflow.dto.GetDiaryBySlug
import run.cd80.tldr.api.diary.workflow.dto.GetDiaryCalendar
import run.cd80.tldr.api.diary.workflow.dto.GetRecentlyViewed
import run.cd80.tldr.api.domain.auth.DefaultSignInUser
import run.cd80.tldr.lib.calendar.Calendar

@RequestMapping("/v1/diaries")
@RestController
class DiaryHttpController(
    private val getDiaryCalendarWorkflow: GetDiaryCalendarWorkflow,
    private val getRecentlyViewedPostWorkflow: GetRecentlyViewedPostWorkflow,
    private val createDiaryWorkflow: CreateDiaryWorkflow,
    private val getDiaryBySlugWorkflow: GetDiaryBySlugWorkflow,
    private val calendar: Calendar,
) {

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/calendar")
    fun getDiaryCalendar(
        @Valid dailyCalendar: DailyCalendar.Request,
        @AuthenticationPrincipal authentication: DefaultSignInUser,
    ): DailyCalendar.Response {
        val response = getDiaryCalendarWorkflow.execute(
            GetDiaryCalendar.Request(
                authentication.name,
                dailyCalendar.now,
            ),
        )

        return DailyCalendar.Response(
            dailyCalendar.now,
            response.items.map {
                DailyCalendar.Response.Diary(it.id, it.title, it.diaryAt, it.createdAt)
            },
        )
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/recently-viewed")
    fun getRecentViews(
        @Valid recentViewed: RecentlyViewed.Request,
        @AuthenticationPrincipal authentication: DefaultSignInUser,
    ): RecentlyViewed.Response {
        val response = getRecentlyViewedPostWorkflow.execute(
            GetRecentlyViewed.Request(
                authentication.name,
                recentViewed.count,
            ),
        )

        return RecentlyViewed.Response(
            response.items.map {
                RecentlyViewed.Response.Diary(it.id, it.title, it.content, it.createdAt)
            },
        )
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    fun createDiary(
        @Valid @RequestBody createDiary: CreateDiaryDto.Request,
        @AuthenticationPrincipal authentication: DefaultSignInUser,
    ): CreateDiaryDto.Response {
        val response = createDiaryWorkflow.execute(
            CreateDiary.Request(
                authentication.name,
                createDiary.title,
                createDiary.content,
                calendar.parse(createDiary.dateTime, "yyyy-MM-dd HH:mm:ss"),
            ),
        )

        return CreateDiaryDto.Response(
            response.id,
            response.title,
            response.content,
        )
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/slug/{slug}")
    fun getDiaryBySlug(
        @PathVariable slug: String,
        @Valid @RequestBody createDiary: GetDiaryBySlugDto.Request,
        @AuthenticationPrincipal authentication: DefaultSignInUser,
    ): GetDiaryBySlugDto.Response {
        val response = getDiaryBySlugWorkflow.execute(
            GetDiaryBySlug.Request(
                slug,
            ),
        )

        return GetDiaryBySlugDto.Response(
            response.id,
            response.title,
        )
    }
}
