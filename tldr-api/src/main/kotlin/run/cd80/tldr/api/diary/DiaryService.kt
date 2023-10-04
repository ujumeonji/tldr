package run.cd80.tldr.api.diary

import org.springframework.stereotype.Service
import run.cd80.tldr.api.config.GithubConfig
import run.cd80.tldr.lib.calendar.Calendar
import run.cd80.tldr.lib.crawler.boj.BojCrawler
import run.cd80.tldr.lib.crawler.boj.dto.GetSolutions
import run.cd80.tldr.lib.crawler.wakatime.WakatimeCrawler
import run.cd80.tldr.lib.crawler.wakatime.dto.GetTaskRecord
import run.cd80.tldr.lib.github.GithubManager
import run.cd80.tldr.lib.github.GithubOption
import run.cd80.tldr.lib.github.dto.UploadFile
import run.cd80.tldr.lib.github.vo.GitRepository
import run.cd80.tldr.lib.github.vo.GithubAccessToken
import run.cd80.tldr.lib.http.HttpClient

@Service
class DiaryService(
    httpClient: HttpClient,
    githubConfig: GithubConfig,
    private val calendar: Calendar,
) {

    private val bojCrawler = BojCrawler(httpClient)

    private val wakatimeCrawler = WakatimeCrawler(httpClient)

    private val githubManager = GithubManager(
        httpClient,
        GithubOption(githubConfig.clientId, githubConfig.clientSecret, githubConfig.redirectUri, githubConfig.scopes),
    )

    suspend fun createDiary() {
        val nowDate = calendar.now().toLocalDate()
        val solutions = bojCrawler.getSolution(
            GetSolutions.Command("test-boj-username"),
        )
        val summaries = wakatimeCrawler.getTaskRecords(
            GetTaskRecord.Command("test-api-key", nowDate),
        )

        githubManager.uploadFile(
            UploadFile.Command(
                "오늘의 일기 $nowDate",
                DiaryContent(summaries, solutions),
                "main",
                "diary/${nowDate.year}/${nowDate.monthValue}/${nowDate.dayOfMonth}.md",
            ),
            GitRepository.of("test-owner", "test-repo"),
            GithubAccessToken.of("test-access-token"),
        )
    }
}
