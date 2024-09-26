package ro.bcostea.book_manager.exceptions

import org.springframework.http.HttpStatus

sealed class ApiError(
    open val errorCode: ErrorCode,
    open val httpStatus: HttpStatus,
    override val message: String
) : RuntimeException(message) {
    open class NotFound(
        override val errorCode: ErrorCode = ErrorCode.NOT_FOUND,
        override val message: String
    ) : ApiError(errorCode, HttpStatus.NOT_FOUND, message)

    open class BadRequest(
        override val errorCode: ErrorCode = ErrorCode.BAD_REQUEST,
        override val message: String
    ) : ApiError(errorCode, HttpStatus.BAD_REQUEST, message)

    open class InternalError(
        override val errorCode: ErrorCode = ErrorCode.INTERNAL_ERROR,
        override val message: String
    ) : ApiError(errorCode, HttpStatus.INTERNAL_SERVER_ERROR, message)

    open class ExternalServiceError(
        override val errorCode: ErrorCode = ErrorCode.EXTERNAL_SERVICE_ERROR,
        override val message: String
    ) : ApiError(errorCode, HttpStatus.SERVICE_UNAVAILABLE, message)

    // Custom exceptions now extend from ApiError
    class BookNotFoundException(id: String) :
        NotFound(message = "Book not found with id: $id")

    class InvalidBookDataException(message: String) :
        BadRequest(message = message)

    class DatabaseOperationException(message: String) :
        InternalError(message = message)

    class ExternalServiceException(message: String) :
        ExternalServiceError(message = message)
}

