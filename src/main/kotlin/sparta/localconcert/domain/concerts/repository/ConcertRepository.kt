package sparta.localconcert.domain.concerts.repository

import org.springframework.data.jpa.repository.JpaRepository
import sparta.localconcert.domain.concerts.model.Concert

interface ConcertRepository: JpaRepository<Concert,Long>,CustomConcertRepository {
}