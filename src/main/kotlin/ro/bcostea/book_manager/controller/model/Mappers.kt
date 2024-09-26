package ro.bcostea.book_manager.controller.model

import ro.bcostea.book_manager.services.model.Book

fun Book.toDto(): BookDto =
    BookDto(
        id = id,
        title = title,
        authors = authors,
        publishedYear = publishedYear,
        languages = languages,
    )
