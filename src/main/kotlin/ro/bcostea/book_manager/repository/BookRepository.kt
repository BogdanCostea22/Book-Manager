package ro.bcostea.book_manager.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ro.bcostea.book_manager.repository.entites.BookEntity
import java.util.UUID

@Repository
interface BookRepository: JpaRepository<BookEntity, UUID> {
    fun findByTitleContainingIgnoreCase(title: String): List<BookEntity>
}
