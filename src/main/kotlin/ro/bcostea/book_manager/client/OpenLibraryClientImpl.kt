package ro.bcostea.book_manager.client

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import ro.bcostea.book_manager.client.model.OpenLibraryBook
import ro.bcostea.book_manager.client.model.OpenLibrarySearchResponse
import ro.bcostea.book_manager.client.model.OpenLibrarySingleBookResponse
import ro.bcostea.book_manager.client.model.toBook
import ro.bcostea.book_manager.config.OpenLibraryProperties
import ro.bcostea.book_manager.exceptions.ApiError
import ro.bcostea.book_manager.services.model.Book

/**
 * Implementation of [OpenLibraryClient] that communicates with the Open Library API
 * using Spring's WebClient.
 *
 * This implementation uses reactive programming concepts for non-blocking I/O operations.
 * It's configured with a base URL for the Open Library API, which is injected via
 * [OpenLibraryProperties].
 *
 * @property properties Configuration properties for the Open Library API.
 */
@Component
class OpenLibraryClientImpl(
    properties: OpenLibraryProperties,
) : OpenLibraryClient {
    private val webClient: WebClient =
        WebClient.builder()
            .codecs { configurer ->
                configurer.defaultCodecs().maxInMemorySize(2 * 1024 * 1024)
            }
            .baseUrl(properties.baseUrl).build()

    /**
     * Fetches a book from the Open Library API by its unique identifier.
     *
     * This method sends a GET request to the Open Library API's "api/get" endpoint
     * with the book's ID as a query parameter. The response is then parsed and
     * converted to our domain [Book] model.
     *
     * @param id The unique identifier of the book in the Open Library system.
     * @return A [Book] object if found, or null if no book matches the given ID.
     * @throws ApiError.ExternalServiceException if there's an error communicating with the Open Library API.
     */
    override fun fetchById(id: String): Book? =
        try {
            webClient.get()
                .uri { builder ->
                    builder.path("/search.json")
                        .queryParam("q", id)
                        .queryParam("limit", 1)
                        .build()
                }
                .retrieve()
                .bodyToMono<OpenLibrarySearchResponse>()
                .block()?.books?.firstOrNull()?.toBook()
        } catch (ex: Exception) {
            throw ApiError.ExternalServiceException("Error fetching book from Open Library API: ${ex.message}")
        }


    @Deprecated("This search api has been deprecated")
    override fun getBookById(id: String): Book? =
        try {
            webClient.get().uri { builder ->
                builder.path("api/get")
                    .queryParam("key", id)
                    .build()
            }.retrieve()
                .bodyToMono<OpenLibrarySingleBookResponse>()
                .block()?.result?.toBook()
        } catch (ex: Exception) {
            throw ApiError.ExternalServiceException("Error fetching book from Open Library API: ${ex.message}")
        }

    /**
     * Searches for books in the Open Library based on their title.
     *
     * This method sends a GET request to the Open Library API's "/search.json" endpoint
     * with the search query as a parameter. The response, which may contain multiple books,
     * is then parsed and converted to a list of our domain [Book] model.
     *
     * @param title The title (or part of the title) to search for.
     * @return A list of [Book] objects matching the search criteria. Returns an empty list if no books are found or an error occured.
     */
    override fun searchAfterTitle(title: String): List<Book> = try {
        webClient.get()
            .uri { builder ->
                builder
                    .path("/search.json")
                    .queryParam("q", title)
                    .queryParam("limit", 10)
                    .build()
            }
            .retrieve()
            .bodyToMono<OpenLibrarySearchResponse>()
            .block()?.books?.map(OpenLibraryBook::toBook) ?: emptyList()
    } catch (e: Exception) {
        emptyList()
    }
}