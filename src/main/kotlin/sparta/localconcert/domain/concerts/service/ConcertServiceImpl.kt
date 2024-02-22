package sparta.localconcert.domain.concerts.service

import com.fasterxml.jackson.core.type.TypeReference
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

    // Local Cache to Redis
    @Transactional(readOnly = true)
    override fun searchCacheConcert(keyword: String, pageable: Pageable): CustomPageImpl<SearchConcertResponse> {
        saveRanking(keyword)

        if (redisConcertRepository.exists("paging::${keyword}_${pageable.pageNumber}_${pageable.pageSize}")) {
            val a =
                redisConcertRepository.getHashValue("paging::${keyword}_${pageable.pageNumber}_${pageable.pageSize}")
            val mapper = mapper.objectMapper()
            val c = a.getValue("page").toString()
            println(c)
            val b = mapper.readValue(c, object : TypeReference<CustomPageImpl<SearchConcertResponse>>() {})
            return b

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

        /*

        return searching

         */
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

