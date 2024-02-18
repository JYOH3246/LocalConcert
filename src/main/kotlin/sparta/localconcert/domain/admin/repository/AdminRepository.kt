package sparta.localconcert.domain.admin.repository

import org.springframework.data.jpa.repository.JpaRepository
import sparta.localconcert.domain.admin.model.Admin

interface AdminRepository: JpaRepository<Admin, Long> {
    fun findByEmail(email: String): Admin?
}