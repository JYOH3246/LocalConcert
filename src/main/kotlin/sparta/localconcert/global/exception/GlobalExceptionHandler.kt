package sparta.localconcert.global.exception

import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.status
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import sparta.localconcert.global.dto.BaseResponse

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(BaseException::class)
    fun handleBaseException(e: BaseException): ResponseEntity<BaseResponse> {
        return status(e.baseResponseCode.status).body(
            BaseResponse(
                status = e.baseResponseCode.status,
                message = e.baseResponseCode.message
            )
        )
    }
}