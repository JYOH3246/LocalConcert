package sparta.localconcert.domain.keyword.model

import jakarta.persistence.*

@Entity
data class Keyword(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val keyword: String,

    @Column(nullable = false)
    var count: Long = 0
)