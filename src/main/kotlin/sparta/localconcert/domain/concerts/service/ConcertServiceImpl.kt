package sparta.localconcert.domain.concerts.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import sparta.localconcert.domain.concerts.dto.request.AddConcertRequest
import sparta.localconcert.domain.concerts.dto.request.UpdateConcertRequest
import sparta.localconcert.domain.concerts.dto.request.toEntity
import sparta.localconcert.domain.concerts.dto.response.FindConcertResponse
import sparta.localconcert.domain.concerts.dto.response.SearchConcertResponse
import sparta.localconcert.domain.concerts.repository.ConcertRepository

@Service
class ConcertServiceImpl(
    private val concertRepository: ConcertRepository,

):ConcertService {

    @Transactional
    override fun addConcert(request: AddConcertRequest) {
         concertRepository.save(request.toEntity())
    }

    @Transactional
    override fun updateConcert(request: UpdateConcertRequest, concertId: Long) {
        val concert = concertRepository.findByIdOrNull(concertId)
            ?: throw Exception("임시처리입니다.")
        //JPA 더티체킹 공부하고 추가 구현.
    }

    @Transactional
    override fun deleteConcert(concertId: Long) {
        val concert = concertRepository.findByIdOrNull(concertId)
            ?: throw Exception("임시처리입니다.")
        concertRepository.delete(concert)
    }
    @Transactional(readOnly = true)
    override fun searchConcert(title: String): List<SearchConcertResponse> {
        return concertRepository.searchConcertByTitle(title).map { SearchConcertResponse.fromEntity(it) }
    }
    @Transactional
    override fun findConcert(concertId: Long): FindConcertResponse {
        val concert = concertRepository.findByIdOrNull(concertId)
            ?: throw Exception("임시처리입니다.")
        return FindConcertResponse.fromEntity(concert)
    }
}


