package ro.bcostea.book_manager

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import ro.bcostea.book_manager.config.OpenLibraryProperties

@SpringBootApplication
@EnableConfigurationProperties(OpenLibraryProperties::class)
class BookManagerApplication

fun main(args: Array<String>) {
	runApplication<BookManagerApplication>(*args)
}
