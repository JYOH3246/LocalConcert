package sparta.localconcert.domain.concerts.service

import sparta.localconcert.domain.concerts.dto.request.AddConcertRequest
import sparta.localconcert.domain.concerts.dto.request.UpdateConcertRequest
import sparta.localconcert.domain.concerts.dto.response.FindConcertResponse
import sparta.localconcert.domain.concerts.dto.response.SearchConcertResponse

interface ConcertService {

    fun addConcert(request: AddConcertRequest)

    fun updateConcert(request: UpdateConcertRequest, concertId: Long)

    fun deleteConcert(concertId: Long)

    fun searchConcert(title: String): List<SearchConcertResponse>

    fun searchCacheConcert(title: String): List<SearchConcertResponse>

    fun findConcert(concertId: Long): FindConcertResponse
}