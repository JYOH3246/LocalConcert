package sparta.localconcert.domain.admin.dto.response

import java.time.LocalDateTime

data class AdminResponse (
    val adminId: String,
    val email: String,
    val password: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)