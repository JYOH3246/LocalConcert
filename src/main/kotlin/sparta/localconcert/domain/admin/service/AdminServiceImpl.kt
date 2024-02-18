package sparta.localconcert.domain.admin.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import sparta.localconcert.domain.admin.dto.request.SignUpWithLoginRequest
import sparta.localconcert.domain.admin.dto.response.LoginResponse
import sparta.localconcert.domain.admin.dto.response.SignUpResponse
import sparta.localconcert.domain.admin.model.Admin
import sparta.localconcert.domain.admin.repository.AdminRepository

@Service
class AdminServiceImpl(
    private val adminRepository: AdminRepository
) : AdminService {

    @Transactional
    override fun signUp(email: String, request: SignUpWithLoginRequest): SignUpResponse {
        val admin = Admin(
            email = request.email,
            password = request.password
        )
        adminRepository.save(admin)

        return SignUpResponse(email)
    }

    @Transactional
    override fun login(request: SignUpWithLoginRequest): LoginResponse {
        val admin = adminRepository.findByEmail(request.email) ?: throw IllegalArgumentException("해당 이메일은 존재하지 않습니다.")

        if (admin.password != request.password) {
            throw IllegalArgumentException("비밀번호가 일치하지 않습니다.")
        }

        return LoginResponse(email = admin.email)
    }
}