package ro.bcostea.book_manager.services.book

import ro.bcostea.book_manager.services.model.Book

/**
 * Service for managing and retrieving book information.
 */
interface BookService {
    /**
     * Creates a new book.
     *
     * @param title The title of the book.
     * @param authors List of authors of the book.
     * @param languages List of languages the book is available in.
     * @return The created Book object.
     */
    fun create(title: String, authors: List<String>, languages: List<String>): Book

    /**
     * Retrieves a book by its ID.
     *
     * @param id The unique identifier of the book.
     * @return The Book object matching the given ID.
     */
    fun getBook(id: String): Book

    /**
     * Retrieves all books.
     *
     * @return A list of all Book objects.
     */
    fun getAll(): List<Book>

    /**
     * Searches for books by title.
     *
     * @param title The title (or part of the title) to search for.
     * @return A list of Book objects matching the search criteria.
     */
    fun search(title: String): List<Book>
}