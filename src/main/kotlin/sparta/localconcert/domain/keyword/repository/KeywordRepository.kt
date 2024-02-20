package sparta.localconcert.domain.keyword.repository

import org.springframework.data.jpa.repository.JpaRepository
import sparta.localconcert.domain.keyword.model.Keyword

interface KeywordRepository : JpaRepository<Keyword, Long>, CustomKeywordRepository {
    fun findByKeyword(keyword: String): Keyword?
}