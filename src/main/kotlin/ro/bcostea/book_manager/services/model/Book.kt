package ro.bcostea.book_manager.services.model

data class Book(
    val id: String,
    val title: String,
    val authors: List<String>,
    val publishedYear: Int,
    val languages: List<String>,
)
