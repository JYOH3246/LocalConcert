package sparta.localconcert.domain.concerts.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import sparta.localconcert.domain.concerts.model.Concert

interface CustomConcertRepository {
    fun searchConcertByTitle(keyword: String, pageable: Pageable): Page<Concert>
}