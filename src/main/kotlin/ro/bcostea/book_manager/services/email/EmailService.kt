package ro.bcostea.book_manager.services.email

import ro.bcostea.book_manager.services.model.Book

/**
 * Service for sending email notifications about books.
 */
interface EmailService {
 /**
  * Sends a notification email about a new book.
  *
  * @param book The new book information to be included in the notification.
  */
 fun sendNewBookNotification(book: Book)
}
