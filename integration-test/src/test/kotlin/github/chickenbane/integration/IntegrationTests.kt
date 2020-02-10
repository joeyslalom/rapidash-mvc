package github.chickenbane.integration

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.math.BigDecimal

@SpringBootTest
class IntegrationTests {

    @Test
    fun contextLoads() {
    }

    @Autowired
    lateinit var restClient: RestClient

    @Test
    fun mvcItems() {
        val actual = restClient.mvcItems().toList()

        val expected = listOf("abc", "def", "ghi")

        assertEquals(expected, actual)
    }

    @Test
    fun mvcItem() {
        val actual = restClient.mvcItem("22")

        assertEquals("best message", actual.message)
        assertEquals(listOf(BigDecimal("12")), actual.numbers)
    }

}
