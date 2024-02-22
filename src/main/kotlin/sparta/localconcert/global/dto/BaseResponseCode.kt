package sparta.localconcert.global.dto

import org.springframework.http.HttpStatus

enum class BaseResponseCode(val status: HttpStatus, val message: String) {
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "잘못된 비밀번호입니다."),
    INVALID_CONCERT(HttpStatus.NOT_FOUND, "공연을 찾을 수 없습니다."),
    INVALID_EMAIL(HttpStatus.NOT_FOUND, "해당 이메일을 찾을 수 없습니다.");

}