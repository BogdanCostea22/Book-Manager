package ro.bcostea.book_manager

import io.zonky.test.db.AutoConfigureEmbeddedDatabase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import ro.bcostea.book_manager.controller.model.CreateBookRequest
import ro.bcostea.book_manager.controller.model.RetrieveBookRequest
import ro.bcostea.book_manager.repository.BookRepository
import kotlin.test.assertEquals

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@AutoConfigureWireMock(port = 0)
@AutoConfigureEmbeddedDatabase
@ActiveProfiles("test")
@Import(WireMockConfig::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BookControllerIntegrationTest {

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @Autowired
    private lateinit var bookRepository: BookRepository

    @BeforeEach
    fun setup() {
        bookRepository.deleteAll()
    }

    @Test
    fun `should create a new book`() {
        val createBookRequest = CreateBookRequest(
            title = "Test Book",
            authors = listOf("Test Author"),
            languages = listOf("en")
        )

        webTestClient.post()
            .uri("/api/v1/books")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(createBookRequest)
            .exchange()
            .expectStatus().isCreated
            .expectBody()
            .jsonPath("$.title").isEqualTo("Test Book")
            .jsonPath("$.authors[0]").isEqualTo("Test Author")
            .jsonPath("$.languages[0]").isEqualTo("en")

        assertEquals(1, bookRepository.count())
    }

    @Test
    @Disabled
    fun `should retrieve a book from Open Library`() {
        webTestClient.post()
            .uri("/api/v1/books/retrieve")
            .bodyValue(RetrieveBookRequest("/works/OL123W"))
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.title").isEqualTo("Example Book")
            .jsonPath("$.authors[0]").isEqualTo("John Doe")
    }

    @Test
    fun `should search for books`() {
        // First, create a book
        val createBookRequest = CreateBookRequest(
            title = "SearchTest Book",
            authors = listOf("Search Author"),
            languages = listOf("en")
        )

        webTestClient.post()
            .uri("/api/v1/books")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(createBookRequest)
            .exchange()
            .expectStatus().isCreated

        // Then, search for it
        webTestClient.get()
            .uri("/api/v1/books/search?title=SearchTest")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$[0].title").isEqualTo("SearchTest Book")
            .jsonPath("$[0].authors[0]").isEqualTo("Search Author")
    }
}