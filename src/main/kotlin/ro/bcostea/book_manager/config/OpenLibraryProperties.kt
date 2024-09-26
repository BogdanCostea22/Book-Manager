package ro.bcostea.book_manager.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("open.library")
class OpenLibraryProperties(
    val baseUrl: String,
)
