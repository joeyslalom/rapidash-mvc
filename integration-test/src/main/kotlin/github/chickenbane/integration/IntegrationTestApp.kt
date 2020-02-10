package github.chickenbane.integration

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class IntegrationTestApp

fun main(args: Array<String>) {
    runApplication<IntegrationTestApp>(*args)
}
