package run.cd80.tldr

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class TldrApplication

fun main(args: Array<String>) {
    runApplication<TldrApplication>(*args)
}
