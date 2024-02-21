package sparta.localconcert.domain.concerts.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.support.PageableExecutionUtils
import org.springframework.stereotype.Repository
import sparta.localconcert.domain.concerts.model.Concert
import sparta.localconcert.domain.concerts.model.QConcert
import sparta.localconcert.global.querydsl.QueryDslSupport


@Repository
class ConcertRepositoryImpl : CustomConcertRepository, QueryDslSupport() {
    private val concert = QConcert.concert
    override fun searchConcertByTitle(keyword: String, pageable: Pageable): Page<Concert> {
        val query = queryFactory.selectFrom(concert)
            .where(concert.title.containsIgnoreCase(keyword))
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())

        val concerts = query.fetch()
        return PageableExecutionUtils.getPage(concerts, pageable) { concerts.size.toLong() }
    }
}