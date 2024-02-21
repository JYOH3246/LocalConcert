package sparta.localconcert.domain.concerts.repository

import org.springframework.stereotype.Repository
import sparta.localconcert.domain.concerts.model.Concert
import sparta.localconcert.domain.concerts.model.QConcert
import sparta.localconcert.global.querydsl.QueryDslSupport

@Repository
class ConcertRepositoryImpl : CustomConcertRepository, QueryDslSupport() {
    private val concert = QConcert.concert
    override fun searchConcertByTitle(keyword: String, page: Int, size: Int): List<Concert> {
        return queryFactory.selectFrom(concert)
            .where(concert.title.containsIgnoreCase(keyword))
            .offset((page - 1) * size.toLong())
            .limit(size.toLong())
            .fetch()
    }
}