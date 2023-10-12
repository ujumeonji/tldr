package run.cd80.tldr.config

import io.kotest.core.config.AbstractProjectConfig
import io.kotest.extensions.spring.SpringExtension
import org.springframework.boot.test.context.TestConfiguration

@TestConfiguration
class KotestTestConfig : AbstractProjectConfig() {

    override fun extensions() = listOf(SpringExtension)
}
