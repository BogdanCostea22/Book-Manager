package ro.bcostea.book_manager.repository.entites

import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "book")
data class BookEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,
    val title: String,
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "book_authors", joinColumns = [JoinColumn(name = "book_id")])
    @Column(name = "author")
    val authors: List<String>,

    @Column(name = "publish_year")
    val publishYear: Int?,

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "book_languages", joinColumns = [JoinColumn(name = "book_id")])
    @Column(name = "language")
    val languages: List<String>
)
