package run.cd80.tldr.lib.crawler.boj

import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import run.cd80.tldr.lib.crawler.boj.dto.GetSolutions
import run.cd80.tldr.lib.crawler.boj.type.JudgeResult
import run.cd80.tldr.lib.http.HttpClient
import run.cd80.tldr.lib.http.dto.HttpResponse
import java.time.LocalDateTime
import java.time.ZoneOffset

class BojCrawler(
    private val httpClient: HttpClient,
) {

    suspend fun getSolution(command: GetSolutions.Command): List<GetSolutions.Result> {
        val response =
            httpClient.get(ACMICPC_STATUS_URL) {
                header("referer", "https://www.acmicpc.net/")
                header("user-agent", "Mozilla/5.0")
                header("content-type", "html/text")
                parameter("user_id", command.username)
            }

        return parseStatus(response).mapNotNull(::getSolutionResult)
    }

    private fun parseStatus(response: HttpResponse): List<Element> =
        Jsoup
            .parseBodyFragment(response.body)
            .getElementById("status-table")
            ?.select("tbody > tr")
            ?: emptyList<Element>()

    private fun getSolutionResult(it: Element): GetSolutions.Result? =
        try {
            val tdElements = it.select("td")
            val solutionId = tdElements[0].text()
            val problemId = tdElements[2].select("a").attr("href").split("/").last()
            val problemTitle = tdElements[2].select("a").attr("title")
            val isCollect = tdElements[3].select("span").text()
            val submittedTimeText = tdElements[8].select("a").attr("data-timestamp")
            val submittedDateTime = LocalDateTime.ofEpochSecond(submittedTimeText.toLong(), 0, ZoneOffset.UTC)

            GetSolutions.Result(
                solutionId.toLong(),
                problemId.toLong(),
                submittedDateTime,
                problemTitle,
                JudgeResult.fromString(isCollect)
            )
        } catch (e: Exception) {
            null
        }

    private companion object {

        private const val ACMICPC_STATUS_URL = "https://www.acmicpc.net/status"
    }
}
