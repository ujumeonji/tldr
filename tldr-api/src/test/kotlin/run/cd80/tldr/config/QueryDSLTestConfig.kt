package run.cd80.tldr.config

import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean

@TestConfiguration
class QueryDSLTestConfig {

    @Bean
    fun jpaQueryFactory(entityManager: EntityManager): JPAQueryFactory =
        JPAQueryFactory(entityManager)
}
