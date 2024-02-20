package sparta.localconcert.domain.concerts.repository

import org.springframework.stereotype.Repository
import sparta.localconcert.domain.concerts.model.Concert
import sparta.localconcert.domain.concerts.model.QConcert.concert
import sparta.localconcert.global.querydsl.QueryDslSupport

@Repository
class ConcertRepositoryImpl : CustomConcertRepository, QueryDslSupport() {
    override fun searchConcertByTitle(title: String): List<Concert> {
        return queryFactory.selectFrom(concert)
            .where(concert.title.containsIgnoreCase(title))
            .fetch()
    }
}