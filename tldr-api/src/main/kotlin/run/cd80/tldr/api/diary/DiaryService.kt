package run.cd80.tldr.api.diary

import org.springframework.stereotype.Service
import run.cd80.tldr.api.crawler.boj.BojCrawler
import run.cd80.tldr.api.crawler.boj.dto.GetSolutions
import run.cd80.tldr.api.crawler.wakatime.WakatimeCrawler
import run.cd80.tldr.api.crawler.wakatime.dto.GetTaskRecord
import run.cd80.tldr.api.manager.github.GithubManager
import run.cd80.tldr.api.manager.github.dto.UploadFile
import run.cd80.tldr.api.manager.github.vo.GitRepository
import run.cd80.tldr.api.manager.github.vo.GithubAccessToken
import run.cd80.tldr.core.calendar.Calendar

@Service
class DiaryService(
    private val bojCrawler: BojCrawler,
    private val wakatimeCrawler: WakatimeCrawler,
    private val githubManager: GithubManager,
    private val calendar: Calendar,
) {

    suspend fun createDiary() {
        val nowDate = calendar.now().toLocalDate()
        val solutions = bojCrawler.getSolution(
            GetSolutions.Command("armhf")
        )
        val summaries = wakatimeCrawler.getTaskRecords(
            GetTaskRecord.Command("waka_dc292d2c-83fd-499e-ad11-33f9c8604e58", nowDate)
        )

        githubManager.uploadFile(
            UploadFile.Command(
                "오늘의 일기 $nowDate",
                DiaryContent(summaries, solutions),
                "main",
                "diary/${nowDate.year}/${nowDate.monthValue}/${nowDate.dayOfMonth}.md",
            ),
            GitRepository.of("dygma0", "diary"),
            GithubAccessToken.of("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")
        )
    }
}
