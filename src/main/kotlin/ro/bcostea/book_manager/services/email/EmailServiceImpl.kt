package ro.bcostea.book_manager.services.email

import net.logstash.logback.argument.StructuredArguments.kv
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ro.bcostea.book_manager.services.model.Book

@Service
class EmailServiceImpl: EmailService {
    override fun sendNewBookNotification(book: Book) {
        logger.info("NOTIFICATION ---- New book has been created!",
            kv("bookId", book.id),
            kv("bookTitle", book.title),
        )
    }

    companion object {
        private val logger =  LoggerFactory.getLogger(EmailServiceImpl::class.java)
    }
}