package sparta.localconcert.domain.keyword.service

import sparta.localconcert.domain.keyword.dto.response.KeywordResponse

interface KeywordService {

    fun incrementKeywordCount(keyword: String)

    fun getPopularKeywords(page: Int, size: Int): List<KeywordResponse>
}