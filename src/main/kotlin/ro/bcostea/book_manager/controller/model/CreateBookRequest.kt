package ro.bcostea.book_manager.controller.model

data class CreateBookRequest(
    val title: String,
    val authors: List<String>,
    val languages: List<String>,
)
