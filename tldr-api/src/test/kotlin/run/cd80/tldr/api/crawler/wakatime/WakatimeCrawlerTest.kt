package run.cd80.tldr.api.crawler.wakatime

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import run.cd80.tldr.api.crawler.wakatime.dto.GetTaskRecord
import run.cd80.tldr.core.http.dto.HttpResponse
import run.cd80.tldr.fixture.StubHttpClient
import run.cd80.tldr.fixture.StubHttpClientFactory
import java.time.LocalDate

class WakatimeCrawlerTest : BehaviorSpec({

    given("Api와 특정 날짜를 전달하여") {
        val httpClient = StubHttpClient()
        val nowDate = LocalDate.of(2023, 9, 30)
        val command = GetTaskRecord.Command("test-api-key", nowDate)
        httpClient.addResponse(
            HttpResponse(
                200,
                """
            {
              "data": [
                {
                  "color": null,
                  "duration": 338.179,
                  "project": "project A",
                  "time": 1695691522.766
                },
                {
                  "color": null,
                  "duration": 1753.73,
                  "project": "project B",
                  "time": 1695691860.945
                }
              ],
              "end": "2023-09-26T14:59:59Z",
              "start": "2023-09-25T15:00:00Z",
              "timezone": "Asia/Seoul"
            }
                """.trimIndent(),
            ),
        )

        `when`("getDurations를 호출한 경우") {
            val results = WakatimeCrawler(StubHttpClientFactory(httpClient)).getTaskRecords(command)

            then("작업 내역을 전달받는다.") {
                httpClient.url shouldBe "https://wakatime.com/api/v1/users/current/durations"
                httpClient.queryParamMap["date"] shouldBe "2023-09-30"
                httpClient.headerMap["Authorization"] shouldBe "Basic dGVzdC1hcGkta2V5"
            }

            then("작업 내역을 전달받는다.") {
                results.size shouldBe 2

                results[0].project shouldBe "project A"
                results[0].duration shouldBe 338.179f
                results[0].workedDateTime.year shouldBe 2023
                results[0].workedDateTime.monthValue shouldBe 9
                results[0].workedDateTime.dayOfMonth shouldBe 26
                results[0].workedDateTime.hour shouldBe 1
                results[0].workedDateTime.minute shouldBe 25
                results[0].workedDateTime.second shouldBe 20

                results[1].project shouldBe "project B"
                results[1].duration shouldBe 1753.73f
                results[1].workedDateTime.year shouldBe 2023
                results[1].workedDateTime.monthValue shouldBe 9
                results[1].workedDateTime.dayOfMonth shouldBe 26
                results[1].workedDateTime.hour shouldBe 1
                results[1].workedDateTime.minute shouldBe 31
                results[1].workedDateTime.second shouldBe 44
            }
        }
    }
})
