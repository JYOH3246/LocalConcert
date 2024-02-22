package sparta.localconcert.domain.concerts.repository

import com.querydsl.core.types.Projections
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import sparta.localconcert.domain.concerts.dto.response.CustomPageImpl
import sparta.localconcert.domain.concerts.dto.response.SearchConcertResponse
import sparta.localconcert.domain.concerts.model.QConcert
import sparta.localconcert.global.querydsl.QueryDslSupport


@Repository
class ConcertRepositoryImpl : CustomConcertRepository, QueryDslSupport() {
    private val concert = QConcert.concert

    override fun searchConcertByTitle(keyword: String, pageable: Pageable): CustomPageImpl<SearchConcertResponse> {
        val totalCount = queryFactory.select(concert.count()).from(concert).fetchOne() ?: 0L
        /*
        val query = queryFactory.selectFrom(concert)
            .where(concert.title.containsIgnoreCase(keyword))
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
                val id: Long?,
         */
        val query = queryFactory.select(
            Projections.constructor(
                SearchConcertResponse::class.java,
                concert.id,
                concert.title,
                concert.location,
                concert.startDate,
                concert.endDate,
                concert.isPriced
            )
        ).from(concert)
            .where(concert.title.containsIgnoreCase(keyword))
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())


        val concerts = query.fetch()
        val paging = CustomPageImpl<SearchConcertResponse>(concerts, pageable.pageNumber, pageable.pageSize, totalCount)
        return paging
    }
}
