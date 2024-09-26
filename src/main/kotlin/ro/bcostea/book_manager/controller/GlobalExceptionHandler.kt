package ro.bcostea.book_manager.controller

import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import ro.bcostea.book_manager.controller.model.ErrorResponse
import ro.bcostea.book_manager.exceptions.ApiError

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(ApiError.BookNotFoundException::class)
    fun handleBookNotFoundException(ex: ApiError.BookNotFoundException, request: WebRequest): ResponseEntity<ErrorResponse> =
        createErrorResponse(ex)

    @ExceptionHandler(ApiError.InvalidBookDataException::class)
    fun handleInvalidBookDataException(ex: ApiError.InvalidBookDataException, request: WebRequest): ResponseEntity<ErrorResponse> =
        createErrorResponse(ex)

    @ExceptionHandler(ApiError.ExternalServiceException::class)
    fun handleExternalServiceException(ex: ApiError.ExternalServiceException, request: WebRequest): ResponseEntity<ErrorResponse> =
        createErrorResponse(ex)

    @ExceptionHandler(ApiError.DatabaseOperationException::class)
    fun handleDatabaseOperationException(ex: ApiError.DatabaseOperationException, request: WebRequest): ResponseEntity<ErrorResponse> =
        createErrorResponse(ex)

    @ExceptionHandler(Exception::class)
    fun handleGenericException(ex: Exception, request: WebRequest): ResponseEntity<ErrorResponse> {
        val apiError = ApiError.InternalError(message = "An unexpected error occurred")
        logger.error("Handling uncaught exception with message {}", ex.message )
        return createErrorResponse(apiError)
    }

    private fun createErrorResponse(apiError: ApiError): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            errorCode = apiError.errorCode.code,
            status = apiError.httpStatus.value(),
            message = apiError.message
        )
        return ResponseEntity(errorResponse, apiError.httpStatus)
    }

    companion object {
        private val logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)
    }
}