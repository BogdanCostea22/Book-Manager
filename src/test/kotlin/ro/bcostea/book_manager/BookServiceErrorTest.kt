package ro.bcostea.book_manager

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import ro.bcostea.book_manager.client.OpenLibraryClient
import ro.bcostea.book_manager.exceptions.ApiError
import ro.bcostea.book_manager.repository.BookRepository
import ro.bcostea.book_manager.repository.entites.BookEntity
import ro.bcostea.book_manager.services.book.BookServiceImpl
import java.util.UUID
import java.util.Optional
import kotlin.test.assertFailsWith

@ExtendWith(MockitoExtension::class)
class BookServiceErrorTest {

    @Mock
    private lateinit var bookRepository: BookRepository

    @Mock
    private lateinit var openLibraryClient: OpenLibraryClient

    @InjectMocks
    private lateinit var bookService: BookServiceImpl

    @Test
    fun `getBook throws BookNotFoundException when book not found`() {
        val bookId = UUID.randomUUID()
        `when`(bookRepository.findById(bookId)).thenReturn(Optional.empty())
        `when`(openLibraryClient.fetchById(bookId.toString())).thenReturn(null)

        assertFailsWith<ApiError.BookNotFoundException> {
            bookService.getBook(bookId.toString())
        }
    }

    @Test
    fun `getBook throws DatabaseOperationException when repository throws exception`() {
        val bookId = UUID.randomUUID()
        `when`(bookRepository.findById(bookId)).thenThrow(RuntimeException("Database error"))

        assertFailsWith<ApiError.DatabaseOperationException> {
            bookService.getBook(bookId.toString())
        }
    }

    @Test
    fun `create throws DatabaseOperationException when repository throws exception`() {
        val bookEntity = BookEntity(
            id = UUID.randomUUID(),
            title = "Test Book",
            authors = listOf("Test Author"),
            publishYear = 2023,
            languages = listOf("en")
        )
        `when`(bookRepository.save(any(BookEntity::class.java))).thenThrow(RuntimeException("Database error"))

        assertFailsWith<ApiError.DatabaseOperationException> {
            bookService.create(bookEntity.title, bookEntity.authors, bookEntity.languages)
        }
    }

    @Test
    fun `getAll throws DatabaseOperationException when repository throws exception`() {
        `when`(bookRepository.findAll()).thenThrow(RuntimeException("Database error"))

        assertFailsWith<ApiError.DatabaseOperationException> {
            bookService.getAll()
        }
    }

    @Test
    fun `search throws InternalError when repository throws exception`() {
        val title = "Test"
        `when`(bookRepository.findByTitleContainingIgnoreCase(title)).thenThrow(RuntimeException("Database error"))

        assertFailsWith<ApiError.InternalError> {
            bookService.search(title)
        }
    }
}