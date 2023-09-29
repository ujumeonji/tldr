package run.cd80.tldr.api.crawler.boj

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import run.cd80.tldr.api.crawler.boj.dto.GetSolutions
import run.cd80.tldr.core.http.HttpClient
import run.cd80.tldr.core.http.HttpClientOption
import run.cd80.tldr.core.http.dto.HttpResponse

class BojCrawlerTest : BehaviorSpec({

    given("BojCrawler") {
        val httpClient = StubHttpClient()
        val username = "cd80"
        val command = GetSolutions.Command(username)
        httpClient.addResponse(HttpResponse(200, """
<table class="table table-striped table-bordered" id="status-table">
    <thead>
        <tr>
            <th style="width: 7%;">제출 번호</th>
            <th style="width: 12%;">아이디</th>
            <th style="width: 9%;">문제</th>
            <th style="width: 24%;">결과</th>
            <th style="width: 9%;">메모리</th>
            <th style="width: 9%;">시간</th>
            <th style="width: 12%;">언어</th>
            <th style="width: 9%;">코드 길이</th>
            <th style="width: 9%;">제출한 시간</th>
        </tr>
    </thead>
    <tbody>
        <tr id="solution-67338447" data-can-view="">
            <td>67338447</td>
            <td><a href="/user/renda0107">renda0107</a></td>
            <td><a href="/problem/9610" rel="tooltip" data-placement="right" title="" class="problem_title tooltip-click" data-original-title="사분면">9610</a></td>
            <td class="result"><span class="result-text result-wa" data-color="wa">틀렸습니다</span></td>
            <td class="memory"></td>
            <td class="time"></td>
            <td>Python 3</td>
            <td>186<span class="b-text"></span></td>
            <td><a href="javascript:void(0);" rel="tooltip" data-placement="top" title="" data-timestamp="1695998320" class="real-time-update show-date" data-method="from-now" data-original-title="2023년 9월 29일 23:38:40">1시간 전</a></td>
        </tr>
    </tbody>
</table>
        """.trimIndent()))

        `when`("getSolutionsByUser") {
            val results = BojCrawler(httpClient).getSolution(command)

            then("return document") {
                results shouldHaveSize 1
                results.first().problemId shouldBe 9610
                results.first().submittedTime shouldBe 1695998320
            }
        }
    }
})

class StubHttpClient : HttpClient, HttpClientOption {

    private lateinit var url: String

    private val queryParamMap = mutableMapOf<String, String>()

    private val headerMap = mutableMapOf<String, String>()

    private lateinit var body: String

    private val response = mutableListOf<HttpResponse>()

    override fun get(url: String): HttpClientOption =
        apply { this.url = url }

    override fun post(url: String): HttpClientOption =
        apply { this.url = url }

    override fun put(url: String): HttpClientOption =
        apply { this.url = url }

    override fun delete(url: String): HttpClientOption =
        apply { this.url = url }

    override fun patch(url: String): HttpClientOption =
        apply { this.url = url }

    override fun queryParam(key: String, value: String): HttpClientOption =
        apply { queryParamMap[key] = value }

    override fun header(key: String, value: String): HttpClientOption =
        apply { headerMap[key] = value }

    override fun body(body: String): HttpClientOption =
        apply { this.body = body }

    override suspend fun execute(): HttpResponse {
        val response = response.first()
        this.response.removeFirst()
        return response
    }

    fun addResponse(response: HttpResponse) {
        this.response.add(response)
    }

    fun clear() {
        queryParamMap.clear()
        headerMap.clear()
        response.clear()
    }
}
