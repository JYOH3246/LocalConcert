package sparta.localconcert.domain.admin.dto.request

import sparta.localconcert.domain.admin.dto.response.SignUpResponse
import sparta.localconcert.domain.admin.model.Admin

data class SignUpWithLoginRequest(
    val email: String,
    val password: String
)

