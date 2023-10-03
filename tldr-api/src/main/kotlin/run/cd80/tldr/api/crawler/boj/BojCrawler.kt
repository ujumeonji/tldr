package run.cd80.tldr.api.crawler.boj

import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.springframework.stereotype.Component
import run.cd80.tldr.api.crawler.boj.dto.GetSolutions
import run.cd80.tldr.core.http.HttpClient
import run.cd80.tldr.core.http.dto.HttpResponse

@Component
class BojCrawler(
    private val httpClient: HttpClient,
) {

    suspend fun getSolution(command: GetSolutions.Command): List<GetSolutions.Result> {
        val response =
            httpClient.get(ACMICPC_STATUS_URL) {
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
            val problemId = tdElements[2].select("a").attr("href").split("/").last()
            val submittedTime = tdElements[8].select("a").attr("data-timestamp")

            GetSolutions.Result(problemId.toLong(), submittedTime.toLong())
        } catch (e: Exception) {
            null
        }

    private companion object {

        private const val ACMICPC_STATUS_URL = "https://www.acmicpc.net/status"
    }
}
