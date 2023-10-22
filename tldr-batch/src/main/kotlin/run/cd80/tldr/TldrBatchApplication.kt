package run.cd80.tldr

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import kotlin.system.exitProcess

@SpringBootApplication
@ConfigurationPropertiesScan
class TldrBatchApplication

fun main(args: Array<String>) {
    val context = runApplication<TldrBatchApplication>(*args)
    val exitCode = SpringApplication.exit(context)
    exitProcess(exitCode)
}
