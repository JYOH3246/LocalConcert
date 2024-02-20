package sparta.localconcert.domain.admin.service

import sparta.localconcert.domain.admin.dto.request.SignUpWithLoginRequest
import sparta.localconcert.domain.admin.dto.request.WithdrawRequest
import sparta.localconcert.domain.admin.dto.response.LoginResponse
import sparta.localconcert.domain.admin.dto.response.SignUpResponse

interface AdminService {

    fun signUp(request: SignUpWithLoginRequest): SignUpResponse

    fun login(request: SignUpWithLoginRequest): LoginResponse

    fun withdraw(adminId: Long, email: String, request: WithdrawRequest)
}