package run.cd80.tldr.api.diary.ui.http

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.restassured.RestAssured
import jakarta.transaction.Transactional
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.MediaType
import run.cd80.tldr.api.diary.ui.http.dto.DailyCalendar
import run.cd80.tldr.api.diary.workflow.GetDiaryCalendarWorkflow
import run.cd80.tldr.api.diary.workflow.dto.GetDiaryCalendar
import run.cd80.tldr.common.AuthenticationSessionFactory
import java.time.LocalDateTime

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DiaryHttpControllerTest @Autowired constructor(
    @LocalServerPort
    private val port: Int,
    private val authenticationSessionFactory: AuthenticationSessionFactory,
    @MockBean
    private val getDiaryCalendarWorkflow: GetDiaryCalendarWorkflow,
) : StringSpec() {

    init {
        RestAssured.port = port

        "작성한 달력이 없는 경우 빈 배열을 반환한다." {
            // given
            val username = "username"
            `when`(
                getDiaryCalendarWorkflow.execute(
                    GetDiaryCalendar.Request(
                        username = username,
                        now = LocalDateTime.of(2023, 10, 1, 0, 0),
                    ),
                ),
            ).thenReturn(GetDiaryCalendar.Response(emptyList()))
            val session = authenticationSessionFactory.create(username)

            // when
            val response =
                RestAssured
                    .given()
                    .sessionId("connect.sid", session)
                    .queryParam("year", "2023")
                    .queryParam("month", "10")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .`when`()
                    .get("/api/diaries/calendar")
                    .then()
                    .statusCode(200)
                    .extract().`as`(DailyCalendar.Response::class.java)

            // then
            response.diaries shouldHaveSize 0
        }
    }
}
