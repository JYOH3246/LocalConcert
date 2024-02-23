package sparta.localconcert.domain.keyword.repository

import org.springframework.stereotype.Repository
import sparta.localconcert.domain.keyword.model.Keyword
import sparta.localconcert.domain.keyword.model.QKeyword
import sparta.localconcert.global.querydsl.QueryDslSupport

@Repository
class KeywordRepositoryImpl : CustomKeywordRepository, QueryDslSupport() {
    private val keyword = QKeyword.keyword1
    override fun findTopKeywords(page: Int, size: Int): List<Keyword> {
        return queryFactory.selectFrom(keyword)
            .orderBy(keyword.count.desc())
            .offset((page - 1) * size.toLong())
            .limit(size.toLong())
            .fetch()
    }
}