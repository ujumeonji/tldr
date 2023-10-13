package run.cd80.tldr.api.diary.ui.http

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.restassured.RestAssured
import jakarta.transaction.Transactional
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import run.cd80.tldr.api.diary.ui.http.dto.CreateDiaryDto
import run.cd80.tldr.api.diary.ui.http.dto.DailyCalendar
import run.cd80.tldr.api.diary.ui.http.dto.RecentlyViewed
import run.cd80.tldr.api.diary.workflow.CreateDiaryWorkflow
import run.cd80.tldr.api.diary.workflow.GetDiaryCalendarWorkflow
import run.cd80.tldr.api.diary.workflow.GetRecentlyViewedPostWorkflow
import run.cd80.tldr.api.diary.workflow.dto.CreateDiary
import run.cd80.tldr.api.diary.workflow.dto.GetDiaryCalendar
import run.cd80.tldr.api.diary.workflow.dto.GetRecentlyViewed
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
    @MockBean
    private val getRecentlyViewedPostWorkflow: GetRecentlyViewedPostWorkflow,
    @MockBean
    private val createDiaryWorkflow: CreateDiaryWorkflow,
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
                    .statusCode(HttpStatus.OK.value())
                    .extract().`as`(DailyCalendar.Response::class.java)

            // then
            response.diaries shouldHaveSize 0
        }

        "달력이 존재하는 경우 기본적인 일기 데이터를 포함한다." {
            // given
            val username = "username"
            val createdAt = LocalDateTime.of(2023, 10, 1, 0, 0)
            `when`(
                getDiaryCalendarWorkflow.execute(
                    GetDiaryCalendar.Request(
                        username = username,
                        now = LocalDateTime.of(2023, 10, 1, 0, 0),
                    ),
                ),
            ).thenReturn(
                GetDiaryCalendar.Response(
                    listOf(
                        GetDiaryCalendar.Response.Item(
                            id = 1L,
                            title = "title",
                            createdAt = createdAt,
                        ),
                    ),
                ),
            )
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
                    .statusCode(HttpStatus.OK.value())
                    .extract().`as`(DailyCalendar.Response::class.java)

            // then
            response.diaries shouldHaveSize 1
            response.diaries[0].id shouldBe 1L
            response.diaries[0].title shouldBe "title"
            response.diaries[0].createdAt shouldBe createdAt
        }

        "로그인하지 않은 사용자는 달력을 조회할 수 없다." {
            // given

            // when
            val response =
                RestAssured
                    .given()
                    .queryParam("year", "2023")
                    .queryParam("month", "10")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .`when`()
                    .get("/api/diaries/calendar")
                    .then().log().all()
                    .statusCode(HttpStatus.FORBIDDEN.value())
        }

        "달력 조회 시 잘못된 유형의 파라미터를 요청한 경우 BAD_REQUEST 에러를 반환한다." {
            // given
            val username = "username"
            val session = authenticationSessionFactory.create(username)

            // when
            val response =
                RestAssured
                    .given()
                    .sessionId("connect.sid", session)
                    .queryParam("year", "3033")
                    .queryParam("month", "33")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .`when`()
                    .get("/api/diaries/calendar")
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())

            // then
        }

        "최근 조회한 달력이 없는 경우 빈 배열을 반환한다." {
            // given
            val username = "username"
            val count = 5
            `when`(
                getRecentlyViewedPostWorkflow.execute(
                    GetRecentlyViewed.Request(
                        username = username,
                        count = count,
                    ),
                ),
            ).thenReturn(GetRecentlyViewed.Response(emptyList()))
            val session = authenticationSessionFactory.create(username)

            // when
            val response =
                RestAssured
                    .given()
                    .sessionId("connect.sid", session)
                    .queryParam("count", count)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .`when`()
                    .get("/api/diaries/recently-viewed")
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .extract().`as`(RecentlyViewed.Response::class.java)

            // then
            response.diaries shouldHaveSize 0
        }

        "최근 조회한 일기 목록을 반환한다." {
            // given
            val username = "username"
            val count = 5
            val createdAt = LocalDateTime.of(2023, 10, 1, 0, 0)
            `when`(
                getRecentlyViewedPostWorkflow.execute(
                    GetRecentlyViewed.Request(
                        username = username,
                        count = count,
                    ),
                ),
            ).thenReturn(
                GetRecentlyViewed.Response(
                    listOf(
                        GetRecentlyViewed.Response.Item(
                            id = 1L,
                            title = "title",
                            content = "",
                            createdAt = createdAt,
                        ),
                    ),
                ),
            )
            val session = authenticationSessionFactory.create(username)

            // when
            val response =
                RestAssured
                    .given()
                    .sessionId("connect.sid", session)
                    .queryParam("count", count)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .`when`()
                    .get("/api/diaries/recently-viewed")
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .extract().`as`(RecentlyViewed.Response::class.java)

            // then
            response.diaries shouldHaveSize 1
            response.diaries[0].id shouldBe 1L
            response.diaries[0].title shouldBe "title"
            response.diaries[0].content shouldBe ""
            response.diaries[0].createdAt shouldBe createdAt
        }

        "최근 조회한 일기는 최대 5개까지만 반환한다." {
            // given
            val username = "username"
            val count = 6
            val session = authenticationSessionFactory.create(username)

            // when
            RestAssured
                .given()
                .sessionId("connect.sid", session)
                .queryParam("count", count)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .`when`()
                .get("/api/diaries/recently-viewed")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())

            // then
        }

        "로그인하지 않은 사용자의 경우 최근 조회한 달력을 조회할 수 없다." {
            // given
            val count = 5

            // when
            RestAssured
                .given()
                .queryParam("count", count)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .`when`()
                .get("/api/diaries/recently-viewed")
                .then()

            // then
        }

        "새로운 글을 작성한다." {
            // given
            val username = "username"
            val diaryAt = LocalDateTime.of(2023, 10, 1, 0, 0)
            val createdAt = LocalDateTime.of(2023, 10, 1, 0, 0)
            `when`(
                createDiaryWorkflow.execute(
                    CreateDiary.Request(
                        username = username,
                        title = "title",
                        content = "content",
                        date = diaryAt,
                    ),
                ),
            ).thenReturn(
                CreateDiary.Response(
                    id = 1L,
                    title = "title",
                    content = "content",
                    createdAt = createdAt,
                ),
            )
            val session = authenticationSessionFactory.create(username)
            val dto = CreateDiaryDto.Request(
                title = "title",
                content = "content",
                date = "2023-10-01",
            )

            // when
            val response =
                RestAssured
                    .given().log().all()
                    .sessionId("connect.sid", session)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(dto)
                    .`when`()
                    .post("/api/diaries")
                    .then().log().all()
                    .statusCode(HttpStatus.OK.value())
                    .extract().`as`(CreateDiaryDto.Response::class.java)

            // then
            response.id shouldBe 1L
            response.title shouldBe "title"
            response.content shouldBe "content"
        }

        "잘못된 유형의 일기 요일을 작성한 경우 BAD_REQUEST 에러를 반환한다." {
            // given
            val username = "username"
            val session = authenticationSessionFactory.create(username)
            val dto = CreateDiaryDto.Request(
                title = "title",
                content = "content",
                date = "20231-10-01",
            )

            // when
            RestAssured
                .given().log().all()
                .sessionId("connect.sid", session)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(dto)
                .`when`()
                .post("/api/diaries")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())

            // then
        }
    }
}
