package sparta.localconcert.domain.concerts.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate
import java.time.LocalTime

data class UpdateConcertRequest(
    val title: String,
    val location: String,
    val contents: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    @Schema(description = "Start time of the event", example = "09:00:00", type = "string", format = "time")
    val startTime: LocalTime,
    @Schema(description = "Start time of the event", example = "10:00:00", type = "string", format = "time")
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
