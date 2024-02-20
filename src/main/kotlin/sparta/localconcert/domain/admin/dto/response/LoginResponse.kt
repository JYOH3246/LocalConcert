package sparta.localconcert.domain.admin.dto.response

data class LoginResponse(
    val accessToken: String,
    val refreshToken: String,
    val message: String
)