package ro.bcostea.book_manager.client

import ro.bcostea.book_manager.services.model.Book

/**
 * Client interface for interacting with the Open Library API.
 *
 * This interface defines methods for fetching book information from the Open Library,
 * a free, editable library catalog. It provides functionality to retrieve books by their
 * unique identifiers and to search for books based on their titles.
 *
 * Implementation of this interface should handle network communication, parsing of
 * responses, and conversion of Open Library data structures to our domain model.
 */
interface OpenLibraryClient {
    /**
     * Retrieve a book from the Open Library API by its unique identifier but is using a d.precated endpoint/
     *
     * @param id The unique identifier of the book in the Open Library system.
     * @return A [Book] object if found, or null if no book matches the given ID.
     * @throws ExternalServiceException if there's an error communicating with the Open Library API.
     */
    fun getBookById(id: String): Book?

    /**
     * Fetches a book from the Open Library API by its unique identifier.
     *
     * @param id The unique identifier of the book in the Open Library system.
     * @return A [Book] object if found, or null if no book matches the given ID.
     * @throws ApiError.ExternalServiceException if there's an error communicating with the Open Library API.
     */
    fun fetchById(id: String): Book?
    /**
     * Searches for books in the Open Library based on their title.
     * @param title The title (or part of the title) to search for.
     * @return A list of [Book] objects matching the search criteria. Returns an empty list if no books are found or an error was thrown
     */
    fun searchAfterTitle(title: String): List<Book>
}
