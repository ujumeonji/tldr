package run.cd80.tldr.api.diary

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class DiaryController(
    private val diaryService: DiaryService,
) {

    @PostMapping("api/v1/diary")
    suspend fun createDiary() {
        diaryService.createDiary()
    }
}
