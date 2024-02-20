package sparta.localconcert.domain.admin.repository

import org.springframework.data.jpa.repository.JpaRepository
import sparta.localconcert.domain.admin.model.AdminRefreshToken

interface AdminRefreshTokenRepository : JpaRepository<AdminRefreshToken, Long>

