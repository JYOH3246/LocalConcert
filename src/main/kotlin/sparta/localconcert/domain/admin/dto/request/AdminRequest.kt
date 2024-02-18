package sparta.localconcert.domain.admin.dto.request

import java.time.LocalDateTime

data class AdminRequest (
    val adminId: String,
    val email: String,
    val password: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)