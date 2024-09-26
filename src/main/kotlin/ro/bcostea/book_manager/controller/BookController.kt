package ro.bcostea.book_manager.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import ro.bcostea.book_manager.controller.model.BookDto
import ro.bcostea.book_manager.controller.model.CreateBookRequest
import ro.bcostea.book_manager.controller.model.RetrieveBookRequest
import ro.bcostea.book_manager.controller.model.toDto
import ro.bcostea.book_manager.exceptions.ApiError
import ro.bcostea.book_manager.services.book.BookService
import ro.bcostea.book_manager.services.email.EmailService
import ro.bcostea.book_manager.services.model.Book

@RestController
@RequestMapping("/api/v1/books")
class BookController(
    private val bookService: BookService,
    private val emailService: EmailService,
) {

    @PostMapping("/retrieve")
    suspend fun getBook(@RequestBody body: RetrieveBookRequest): BookDto {
        body.id.ifEmpty { throw ApiError.InvalidBookDataException("Book id is empty!") }
        return bookService.getBook(body.id).toDto()
    }

    @GetMapping
    fun getAllBooks(): List<BookDto> =
        bookService.getAll().map(Book::toDto)

    @GetMapping("/search")
    fun searchBooks(@RequestParam title: String): List<BookDto> =
        bookService.search(title).map(Book::toDto)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addBook(@RequestBody request: CreateBookRequest): Any = with(request) {
        validateRequest(request)
        val savedBook = bookService.create(
            title = title,
            authors = authors,
            languages = languages,
        )

        emailService.sendNewBookNotification(savedBook)
        return savedBook.toDto()
    }

    private fun validateRequest(request: CreateBookRequest) {
        with(request) {
            if (title.isBlank() || authors.isEmpty() || languages.isEmpty()) {
                throw ApiError.InvalidBookDataException("Invalid data!")
            }
        }
    }
}



