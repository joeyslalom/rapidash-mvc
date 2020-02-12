package github.chickenbane.integration

import github.chickenbane.integration.test.IntegrationTests
import org.junit.platform.engine.TestDescriptor
import org.junit.platform.engine.discovery.DiscoverySelectors
import org.junit.platform.launcher.Launcher
import org.junit.platform.launcher.LauncherDiscoveryRequest
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder
import org.junit.platform.launcher.core.LauncherFactory
import org.junit.platform.launcher.listeners.LoggingListener
import org.junit.platform.launcher.listeners.SummaryGeneratingListener
import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.ExitCodeGenerator
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.context.support.beans
import java.io.ByteArrayOutputStream
import java.io.PrintWriter
import kotlin.system.exitProcess

@SpringBootApplication
class IntegrationTestApp

fun main(args: Array<String>) {
    val context = SpringApplicationBuilder().initializers(
            beans {
                bean {
                    Junit5Runner(listOf(IntegrationTests::class.java))
                }
            }
    ).sources(IntegrationTestApp::class.java).run(*args);

    exitProcess(SpringApplication.exit(context))
}

class Junit5Runner(private val testClasses: List<Class<*>>) : ApplicationRunner, ExitCodeGenerator {
    private val log = LoggerFactory.getLogger(Junit5Runner::class.java)

    private var exitCode = 0

    override fun getExitCode(): Int = exitCode

    override fun run(args: ApplicationArguments?) {
        val selectors = testClasses.map { DiscoverySelectors.selectClass(it) }.toTypedArray()
        val request = LauncherDiscoveryRequestBuilder.request()
                .selectors(*selectors)
                .build()

        val launcher = LauncherFactory.create()

        logTests(launcher, request)

        val listener = SummaryGeneratingListener()
        val logListener = LoggingListener.forBiConsumer { t, u ->
            val msg = u.get()
            if (t == null) {
                log.info("test message=$msg")
            } else {
                log.error("test error message=$msg", t)
            }
        }
        launcher.registerTestExecutionListeners(listener, logListener)
        launcher.execute(request)

        val stream = ByteArrayOutputStream()
        val writer = PrintWriter(stream)

        if (listener.summary.totalFailureCount > 0) {
            listener.summary.printFailuresTo(writer)
            log.error("Junit Failures:$stream")
            exitCode = 2
        }

        listener.summary.printTo(writer)
        log.info("Junit Summary:$stream")
    }

    // logs tests found for the request, optional
    private fun logTests(launcher: Launcher, request: LauncherDiscoveryRequest) {
        val testPlan = launcher.discover(request)

        testPlan.roots
                .flatMap { testPlan.getDescendants(it) }
                .filter { it.type == TestDescriptor.Type.TEST }
                .forEach {
                    log.info("uniqueId=${it.uniqueId} displayName=${it.displayName}")
                }
    }

}