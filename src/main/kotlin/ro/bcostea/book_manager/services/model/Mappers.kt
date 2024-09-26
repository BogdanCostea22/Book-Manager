package ro.bcostea.book_manager.services.model

import ro.bcostea.book_manager.repository.entites.BookEntity

fun BookEntity.toDomain(): Book =
    Book(
        id = id.toString(),
        title = title,
        authors = authors,
        publishedYear = publishYear ?: 0,
        languages = languages
    )
