package run.cd80.tldr.api.diary.ui.http

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import run.cd80.tldr.api.diary.ui.http.dto.DailyCalendar
import java.time.LocalDateTime

@RequestMapping("/diaries")
@RestController
class DiaryHttpController {

    @GetMapping("/calendar")
    fun getDiaries(
        dailyCalendar: DailyCalendar.Request,
    ): DailyCalendar.Response = DailyCalendar.Response(LocalDateTime.now(), listOf())
}
