package ro.bcostea.book_manager.exceptions

enum class ErrorCode(val code: String) {
    NOT_FOUND("ERR_001"),
    BAD_REQUEST("ERR_002"),
    INTERNAL_ERROR("ERR_003"),
    EXTERNAL_SERVICE_ERROR("ERR_004")
}
