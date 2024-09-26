package ro.bcostea.book_manager.client.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import ro.bcostea.book_manager.services.model.Book

@JsonIgnoreProperties(ignoreUnknown = true)
class OpenLibraryBook(
    @JsonProperty("key")
    val key: String,
    @JsonProperty("title")
    val title: String,
    @JsonProperty("author_name")
    val authors: List<String>?,
    @JsonProperty("first_publish_year")
    val publishYear: Int?,
    @JsonProperty("language")
    val languages: List<String>?
)

fun OpenLibraryBook.toBook() = Book(
    id = key,
    title = title,
    authors = authors ?: emptyList(),
    publishedYear =  publishYear ?: 0,
    languages = languages ?: emptyList(),
)
