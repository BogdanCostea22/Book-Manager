package ro.bcostea.book_manager.client.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class OpenLibrarySingleBookResponse(
    @JsonProperty("result")
    val result: OpenLibraryBook,
)