package sparta.localconcert.domain.concerts.dto.response

import sparta.localconcert.domain.concerts.model.Concert
import java.time.LocalDate
import java.time.LocalTime

data class FindConcertResponse(
    val id: Long?,
    val title: String,
    val location: String,
    val contents: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val isPriced: Boolean,
    val host: String,
    val organizer: String,
    val price: String,
    val ratings: String,
    val notice: String,
    val pageUrl: String,
    val ticketUrl: String,
    val address: String

) {
    companion object {
        fun fromEntity(concert: Concert) = FindConcertResponse(
            id = concert.id,
            title = concert.title,
            location = concert.location,
            contents = concert.contents,
            startDate = concert.startDate,
            endDate = concert.endDate,
            startTime = concert.startTime,
            endTime = concert.endTime,
            isPriced = concert.isPriced,
            host = concert.host,
            organizer = concert.organizer,
            price = concert.price,
            ratings = concert.ratings,
            notice = concert.notice,
            pageUrl = concert.pageUrl,
            ticketUrl = concert.ticketUrl,
            address = concert.address
        )

    }
}
