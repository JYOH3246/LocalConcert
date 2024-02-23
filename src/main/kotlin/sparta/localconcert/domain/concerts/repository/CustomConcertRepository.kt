package sparta.localconcert.domain.concerts.repository

import org.springframework.data.domain.Pageable
import sparta.localconcert.domain.concerts.dto.response.CustomPageImpl
import sparta.localconcert.domain.concerts.dto.response.SearchConcertResponse

interface CustomConcertRepository {
    fun searchConcertByTitle(keyword: String, pageable: Pageable): CustomPageImpl<SearchConcertResponse>
}