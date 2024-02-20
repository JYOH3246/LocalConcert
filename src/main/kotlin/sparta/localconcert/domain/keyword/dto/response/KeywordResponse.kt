package sparta.localconcert.domain.keyword.dto.response

import sparta.localconcert.domain.keyword.model.Keyword

data class KeywordResponse(
    val keyword: String,
    val count: Long
) {
    companion object {
        fun fromEntity(keyword: Keyword) = KeywordResponse(
            keyword = keyword.keyword,
            count = keyword.count
        )
    }
}
