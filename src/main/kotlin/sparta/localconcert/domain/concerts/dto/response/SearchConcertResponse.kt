package sparta.localconcert.domain.concerts.dto.response

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import sparta.localconcert.domain.concerts.model.Concert
import java.io.Serializable
import java.time.LocalDate

data class SearchConcertResponse @JsonCreator(mode = JsonCreator.Mode.PROPERTIES) constructor(
    @JsonProperty("id") val id: Long?,
    @JsonProperty("title") val title: String,
    @JsonProperty("location") val location: String,
    @JsonProperty("startDate") val startDate: LocalDate,
    @JsonProperty("endDate") val endDate: LocalDate,
    @JsonProperty("priced") val isPriced: Boolean,

    ) : Serializable {
    companion object {
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
