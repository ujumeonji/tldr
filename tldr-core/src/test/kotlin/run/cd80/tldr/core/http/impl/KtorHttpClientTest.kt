package run.cd80.tldr.core.http.impl

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class KtorHttpClientTest : BehaviorSpec({
    given("주어진 url로") {
        val url = "https://www.google.com"

        `when`("get 요청을 보내면") {
            val response = KtorHttpClient().get(url) {
            }

            then("200 OK 응답을 받는다") {
                response.statusCode shouldBe 200
            }
        }
    }
})
