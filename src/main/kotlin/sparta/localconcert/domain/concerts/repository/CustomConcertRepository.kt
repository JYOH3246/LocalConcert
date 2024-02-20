package sparta.localconcert.domain.concerts.repository

import sparta.localconcert.domain.concerts.model.Concert

interface CustomConcertRepository {
    fun searchConcertByTitle(title: String): List<Concert>
}