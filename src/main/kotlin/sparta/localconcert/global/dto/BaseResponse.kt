package sparta.localconcert.global.dto

import org.springframework.http.HttpStatus

data class BaseResponse(
    val status: HttpStatus,
    val message: String
)
