package ro.bcostea.book_manager.controller.model

data class BookDto(
    val id: String,
    val title: String,
    val authors: List<String>,
    val publishedYear: Int,
    val languages: List<String>,
)
