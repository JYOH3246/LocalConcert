package sparta.localconcert.domain.admin.service

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import sparta.localconcert.domain.admin.dto.request.SignUpWithLoginRequest
import sparta.localconcert.domain.admin.dto.request.WithdrawRequest
import sparta.localconcert.domain.admin.dto.response.LoginResponse
import sparta.localconcert.domain.admin.dto.response.SignUpResponse
import sparta.localconcert.domain.admin.model.Admin
import sparta.localconcert.domain.admin.repository.AdminRepository
import sparta.localconcert.global.security.jwt.JwtPlugin

@Service
class AdminServiceImpl(
    private val adminRepository: AdminRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtPlugin: JwtPlugin
) : AdminService {

    @Transactional
    override fun signUp(email: String, request: SignUpWithLoginRequest): SignUpResponse {
        val admin = Admin(
            email = request.email,
            password = passwordEncoder.encode(request.password)
        )
        adminRepository.save(admin)

        return SignUpResponse(email)
    }

    @Transactional
    override fun login(request: SignUpWithLoginRequest): LoginResponse {
        val admin = adminRepository.findByEmail(request.email) ?: throw IllegalArgumentException("해당 이메일은 존재하지 않습니다.")

        if (!passwordEncoder.matches(request.password, admin.password)) {
            throw IllegalArgumentException("비밀번호가 일치하지 않습니다.")
        }

        return LoginResponse(
            accessToken = jwtPlugin.generateAccessToken(email = request.email)
        )
    }

    override fun withdraw(email: String, request: WithdrawRequest) {
        val admin = adminRepository.findByEmail(email)

        // 비밀번호 재입력 받은 후 탈퇴 진행
        if (admin != null) {
            if (!passwordEncoder.matches(admin.password, request.password)) {
                throw IllegalArgumentException("입력하신 비밀번호와 기존 비밀번호가 일치하지 않습니다.")
            } else {
                adminRepository.delete(admin)
            }
        }
    }
}