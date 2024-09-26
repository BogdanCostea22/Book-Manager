package ro.bcostea.book_manager

import io.zonky.test.db.AutoConfigureEmbeddedDatabase
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.Profile

@SpringBootTest
@Profile("test")
@AutoConfigureEmbeddedDatabase
@Import(WireMockConfig::class)
class BookManagerApplicationTests {

	@Test
	fun contextLoads() {
	}
}
