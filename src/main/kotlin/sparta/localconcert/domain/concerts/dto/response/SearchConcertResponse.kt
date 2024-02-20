package sparta.localconcert.domain.concerts.dto.response

import sparta.localconcert.domain.concerts.model.Concert
import java.time.LocalDate

data class SearchConcertResponse(
    val id: Long?,
    val title: String,
    val location: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val isPriced: Boolean,
){
    companion object{
        fun fromEntity(concert: Concert) = SearchConcertResponse(
            id = concert.id,
            title = concert.title,
            location = concert.location,
            startDate = concert.startDate,
            endDate = concert.endDate,
            isPriced = concert.isPriced,
        )
    }
}
