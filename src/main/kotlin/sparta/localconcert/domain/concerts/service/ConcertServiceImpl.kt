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
import sparta.localconcert.domain.concerts.dto.response.CustomPageImpl
import sparta.localconcert.domain.concerts.dto.response.FindConcertResponse
import sparta.localconcert.domain.concerts.dto.response.SearchConcertResponse
import sparta.localconcert.domain.concerts.model.Concert
import sparta.localconcert.domain.concerts.repository.ConcertRepository
import sparta.localconcert.domain.concerts.repository.RedisConcertRepository
import sparta.localconcert.global.objectmapper.MapperConfig

@Service
class ConcertServiceImpl(
    private val concertRepository: ConcertRepository,
    private val redisConcertRepository: RedisConcertRepository,
    private val mapper: MapperConfig,
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
        /*
        concerts.forEach {
            it.searches += 1
            concertRepository.save(it)
        }

         */
        return concerts

    }
    /*
        @Transactional(readOnly = true)
        override fun searchCacheConcert(keyword: String, pageable: Pageable): CustomPageImpl<SearchConcertResponse> {
            saveRanking(keyword)

            if (redisConcertRepository.exists("paging::${keyword}_${pageable.pageNumber}_${pageable.pageSize}")) {
                val getJson =
                    redisConcertRepository.getHashValue("paging::${keyword}_${pageable.pageNumber}_${pageable.pageSize}")
                val mapper = mapper.objectMapper()
                val page = a.getValue("page").toString()
                return mapper.readValue(c, object : TypeReference<CustomPageImpl<SearchConcertResponse>>() {})

            } else {
                val searching =
                    concertRepository.searchConcertByTitle(keyword, pageable)
                redisConcertRepository.saveZSetJsonData("keyword::${keyword}", searching)
                redisConcertRepository.saveHashJsonData(
                    "paging::${keyword}_${pageable.pageNumber}_${pageable.pageSize}",
                    searching
                )
                return searching
            }
        }
     */

    @Cacheable(key = "#keyword", value = ["searching"])
    override fun searchAopConcert(keyword: String, pageable: Pageable): CustomPageImpl<SearchConcertResponse> {

        val searching =
            concertRepository.searchConcertByTitle(keyword, pageable)
        return searching
    }

    @Transactional
    override fun findConcert(concertId: Long): FindConcertResponse {
        val concert = concertRepository.findByIdOrNull(concertId)
            ?: throw Exception("임시처리입니다.")
        return FindConcertResponse.fromEntity(concert)
    }

    fun saveRanking(keyword: String) {
        return redisConcertRepository.saveZSetData("searchRanking", keyword)
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

