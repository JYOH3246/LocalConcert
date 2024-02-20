package sparta.localconcert.domain.keyword.repository

import sparta.localconcert.domain.keyword.model.Keyword


interface CustomKeywordRepository {
    fun findTopKeywords(page: Int, size: Int): List<Keyword>
}