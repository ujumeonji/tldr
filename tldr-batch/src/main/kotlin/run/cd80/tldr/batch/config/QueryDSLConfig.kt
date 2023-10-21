package run.cd80.tldr.batch.config

import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@Configuration
@EnableJpaAuditing
class QueryDSLConfig {

    @Bean
    fun paQueryFactory(entityManager: EntityManager): JPAQueryFactory =
        JPAQueryFactory(entityManager)
}
