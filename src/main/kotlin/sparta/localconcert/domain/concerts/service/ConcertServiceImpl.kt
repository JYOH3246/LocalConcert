package sparta.localconcert.domain.concerts.service

import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import sparta.localconcert.domain.concerts.dto.request.AddConcertRequest
import sparta.localconcert.domain.concerts.dto.request.UpdateConcertRequest
import sparta.localconcert.domain.concerts.dto.request.toEntity
import sparta.localconcert.domain.concerts.dto.response.FindConcertResponse
import sparta.localconcert.domain.concerts.dto.response.SearchConcertResponse
import sparta.localconcert.domain.concerts.model.Concert
import sparta.localconcert.domain.concerts.repository.ConcertRepository


@Service
class ConcertServiceImpl(
    private val concertRepository: ConcertRepository,
) : ConcertService {

    @Transactional
    override fun addConcert(request: AddConcertRequest) {
        concertRepository.save(request.toEntity())
    }

    @Transactional
    override fun updateConcert(request: UpdateConcertRequest, concertId: Long) {
        val concert = concertRepository.findByIdOrNull(concertId)
            ?: throw Exception("임시처리입니다.")
        concert.update(request)
    }

    @Transactional
    override fun deleteConcert(concertId: Long) {
        val concert = concertRepository.findByIdOrNull(concertId)
            ?: throw Exception("임시처리입니다.")
        concertRepository.delete(concert)
    }


    @Transactional(readOnly = true)
    override fun searchConcert(keyword: String, pageable: Pageable): Page<SearchConcertResponse> {
        val concerts = concertRepository.searchConcertByTitle(keyword, pageable)
        concerts.forEach {
            it.searches += 1
            concertRepository.save(it)
        }
        return concerts.map { SearchConcertResponse.fromEntity(it) }
    }

    @Transactional(readOnly = true)
    @Cacheable(value = ["concert"], key = "#keyword")
    override fun searchCacheConcert(keyword: String, pageable: Pageable): Page<SearchConcertResponse> {
        return concertRepository.searchConcertByTitle(keyword, pageable).map { SearchConcertResponse.fromEntity(it) }
    }

    @Transactional
    override fun findConcert(concertId: Long): FindConcertResponse {
        val concert = concertRepository.findByIdOrNull(concertId)
            ?: throw Exception("임시처리입니다.")
        return FindConcertResponse.fromEntity(concert)
    }
}

fun Concert.update(request: UpdateConcertRequest) {
    this.title = request.title
    this.location = request.location
    this.contents = request.contents
    this.startDate = request.startDate
    this.endDate = request.endDate
    this.startTime = request.startTime
    this.endTime = request.endTime
    this.isPriced = request.isPriced
    this.host = request.host
    this.organizer = request.organizer
    this.price = request.price
    this.ratings = request.ratings
    this.notice = request.notice
    this.pageUrl = request.pageUrl
    this.ticketUrl = request.ticketUrl
    this.address = request.address
}


