package run.cd80.tldr.api.diary.ui.http

import io.kotest.core.spec.style.StringSpec
import io.restassured.RestAssured
import jakarta.persistence.EntityManager
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.MediaType
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
            val account = Account.signUp("test@example.com", "test")
            entityManager.persist(account)

            // when
            val response =
                RestAssured
                    .given()
                    .log().all()
                    .sessionId("connect.sid", authenticationSessionFactory.create(account))
                    .queryParam("month", "2021-01")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .`when`()
                    .get("/api/diaries/calendar")
                    .then()
                    .statusCode(200)
                    .extract()

            // then
            val now = response.path<String>("now")
        }
    }
}
