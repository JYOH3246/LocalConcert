package sparta.localconcert.domain.concerts.dto.request

import sparta.localconcert.domain.concerts.model.Concert
import java.time.LocalDate
import java.time.LocalTime

data class AddConcertRequest(
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
    val address: String,
)

fun AddConcertRequest.toEntity(): Concert {
    return Concert(
        title = this.title,
        location = this.location,
        contents = this.contents,
        startDate = this.startDate,
        endDate = this.endDate,
        startTime = this.startTime,
        endTime = this.endTime,
        isPriced = this.isPriced,
        host = this.host,
        organizer = this.organizer,
        price = this.price,
        ratings = this.ratings,
        notice = this.notice,
        pageUrl = this.pageUrl,
        ticketUrl = this.ticketUrl,
        address = this.address
    )
}