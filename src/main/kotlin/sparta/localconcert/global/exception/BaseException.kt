package sparta.localconcert.global.exception

import sparta.localconcert.global.dto.BaseResponseCode

class BaseException(val baseResponseCode: BaseResponseCode) : RuntimeException()