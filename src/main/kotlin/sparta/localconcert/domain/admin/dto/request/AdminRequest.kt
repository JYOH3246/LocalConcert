package sparta.localconcert.domain.admin.dto.request

import java.time.LocalDateTime

data class AdminRequest(
    val adminId: Long,
    val email: String,
    val password: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)