package ro.bcostea.book_manager.extensions

import java.util.UUID

fun String.toUUIDOrNull() = try {
    UUID.fromString(this)
} catch (exc: Exception) {
    null
}