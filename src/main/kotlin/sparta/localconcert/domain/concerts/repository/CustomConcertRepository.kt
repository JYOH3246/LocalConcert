package sparta.localconcert.domain.concerts.repository

import sparta.localconcert.domain.concerts.model.Concert

interface CustomConcertRepository {
    fun searchConcertByTitle(keyword: String, page: Int, size: Int): List<Concert>
}