package ro.bcostea.book_manager.controller.model

data class ErrorResponse(
    val errorCode: String,
    val status: Int,
    val message: String
)
