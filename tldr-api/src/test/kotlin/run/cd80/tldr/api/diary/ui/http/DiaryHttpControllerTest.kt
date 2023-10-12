package run.cd80.tldr.api.diary.ui.http

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.restassured.RestAssured
import jakarta.persistence.EntityManager
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import run.cd80.tldr.api.diary.ui.http.dto.DailyCalendar
import run.cd80.tldr.api.domain.post.Post
import run.cd80.tldr.api.domain.user.Account
import run.cd80.tldr.common.AuthenticationSessionFactory

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DiaryHttpControllerTest @Autowired constructor(
    private val authenticationSessionFactory: AuthenticationSessionFactory,
    private val entityManager: EntityManager,
    @LocalServerPort private val port: Int,
) : StringSpec() {

    init {
        RestAssured.port = port
        RestAssured.baseURI = "http://localhost"

        "일기를 작성한 달력을 조회한다." {
            // given
            val account = Account.signUp("test@example.com")
            entityManager.persist(account)
            val post = Post.create("test title", "test content", account)
            entityManager.persist(post)

            // when
            val response =
                RestAssured
                    .given()
                    .log().all()
                    .sessionId("connect.sid", authenticationSessionFactory.create(account))
                    .queryParam("month", "2023-10")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .`when`()
                    .get("/api/diaries/calendar")
                    .then()
                    .log().all()
                    .statusCode(200)
                    .extract()
                    .`as`(DailyCalendar.Response::class.java)

            // then
            response.diaries shouldHaveSize 1
        }

        "로그인하지 않은 사용자는 일기를 작성한 달력을 조회할 수 없다." {
            // given

            // when
            val response =
                RestAssured
                    .given()
                    .log().all()
                    .queryParam("month", "2021-01")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .`when`()
                    .get("/api/diaries/calendar")
                    .then()
                    .statusCode(HttpStatus.UNAUTHORIZED.value())
                    .extract()

            // then
        }
    }
}
