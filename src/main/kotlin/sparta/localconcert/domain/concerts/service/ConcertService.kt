package sparta.localconcert.domain.concerts.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import sparta.localconcert.domain.concerts.dto.request.AddConcertRequest
import sparta.localconcert.domain.concerts.dto.request.UpdateConcertRequest
import sparta.localconcert.domain.concerts.dto.response.FindConcertResponse
import sparta.localconcert.domain.concerts.dto.response.SearchConcertResponse

interface ConcertService {

    fun addConcert(request: AddConcertRequest)

    fun updateConcert(request: UpdateConcertRequest, concertId: Long)

    fun deleteConcert(concertId: Long)

    fun searchConcert(keyword: String, pageable: Pageable): Page<SearchConcertResponse>

    fun searchCacheConcert(keyword: String, pageable: Pageable): Page<SearchConcertResponse>

    fun findConcert(concertId: Long): FindConcertResponse
    fun searchRanking(): Set<Any>

}