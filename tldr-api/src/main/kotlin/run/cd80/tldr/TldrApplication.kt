package run.cd80.tldr

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TldrApplication

fun main(args: Array<String>) {
    runApplication<TldrApplication>(*args)
}
