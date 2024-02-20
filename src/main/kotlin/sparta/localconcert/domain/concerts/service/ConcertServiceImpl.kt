package sparta.localconcert.domain.concerts.service

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
import sparta.localconcert.domain.concerts.repository.RedisConcertRepository
import sparta.localconcert.global.config.ModuleConfig

@Service
class ConcertServiceImpl(
    private val concertRepository: ConcertRepository,
    private val redisConcertRepository: RedisConcertRepository,
    private val objectMapper: ModuleConfig
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


    @Transactional
    override fun searchConcert(keyword: String): List<SearchConcertResponse> {
        val concerts = concertRepository.searchConcertByTitle(keyword)
        concerts.forEach {
            it.searches += 1
            concertRepository.save(it)
        }
        return concerts.map { SearchConcertResponse.fromEntity(it) }
    }

    // Local Cache to Redis
    @Transactional(readOnly = true)
    // @Cacheable(value = ["concert"], key = "#title")
    override fun searchCacheConcert(title: String): List<SearchConcertResponse> {
        saveRanking(title)
        if (redisConcertRepository.exists("keyword::${title}")) {
            val searching = redisConcertRepository.getZSetValue("keyword::${title}")
            val concertList : MutableList<Concert> = mutableListOf()
            for (element in searching) {
                redisConcertRepository.saveZSetData("keyword::${title}", element)
                concertList+=objectMapper.objectMapper().readValue(element.toString(),Concert::class.java)
            }
            return concertList.map { SearchConcertResponse.fromEntity(it) }
        } else {
            val searching = concertRepository.searchConcertByTitle(title)
            redisConcertRepository.saveZSetJsonData("keyword::${title}", searching)
            return searching.map { SearchConcertResponse.fromEntity(it) }
        }
    }

    @Transactional
    override fun findConcert(concertId: Long): FindConcertResponse {
        val concert = concertRepository.findByIdOrNull(concertId)
            ?: throw Exception("임시처리입니다.")
        return FindConcertResponse.fromEntity(concert)
    }

    fun saveRanking(title: String) {
        return redisConcertRepository.saveZSetData("searchRanking", title)
    }
    override fun searchRanking(): Set<Any> {
        return redisConcertRepository.getSearchRanking("searchRanking")
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


