package run.cd80.tldr.config

import io.kotest.core.config.AbstractProjectConfig
import io.kotest.extensions.spring.SpringExtension

class KotestConfig : AbstractProjectConfig() {

    override fun extensions() = listOf(SpringExtension)
}
