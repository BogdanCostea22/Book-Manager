package ro.bcostea.book_manager.services.book

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ro.bcostea.book_manager.client.OpenLibraryClient
import ro.bcostea.book_manager.exceptions.ApiError
import ro.bcostea.book_manager.extensions.toUUIDOrNull
import ro.bcostea.book_manager.repository.BookRepository
import ro.bcostea.book_manager.repository.entites.BookEntity
import ro.bcostea.book_manager.services.model.Book
import ro.bcostea.book_manager.services.model.toDomain
import java.time.Year
import kotlin.jvm.optionals.getOrNull

@Service
class BookServiceImpl(
    private val repository: BookRepository,
    private val openLibraryClient: OpenLibraryClient,
) : BookService {

    override fun create(title: String, authors: List<String>, languages: List<String>): Book =
        try {
            repository.save(
                BookEntity(
                    title = title,
                    authors = authors,
                    publishYear = Year.now().value,
                    languages = languages,
                )
            ).toDomain()
        } catch (exc: Exception) {
            logger.error("Failed to save book because of error ${exc.message}")
            throw ApiError.DatabaseOperationException(exc.message ?: "Error when creating the book.")
        }


    override fun getBook(id: String): Book =
        try {
            id.toUUIDOrNull()?.let(repository::findById)?.map(BookEntity::toDomain)?.getOrNull()
                ?: openLibraryClient.fetchById(id) ?: throw ApiError.BookNotFoundException(id)
        } catch (exc: Exception) {
            logger.error("And error with messgae {} was thrown while fetching the book with id {}", exc.message, id)
            when (exc) {
                is ApiError -> throw exc
                else -> throw ApiError.DatabaseOperationException("Error retrieving book with id: $id")
            }
        }

    override fun getAll(): List<Book> =
        try {
            repository.findAll()
                .map(BookEntity::toDomain)
        } catch (exc: Exception) {
            logger.error("Failed to return all the books!")
            throw ApiError.DatabaseOperationException("Error when retrieving books!")
        }

    override fun search(title: String): List<Book> =
        try {
            val localBooks = repository.findByTitleContainingIgnoreCase(title = title)
                .map(BookEntity::toDomain)
            val booksFromRemoteSource = openLibraryClient.searchAfterTitle(title)

            localBooks + booksFromRemoteSource
        } catch (exc: Exception) {
            logger.error("Failed to search book with title {}", title)
            throw ApiError.InternalError(message = "Failed to search book with title $title")
        }

    companion object {
        private val logger = LoggerFactory.getLogger(BookServiceImpl::class.java)
    }
}
