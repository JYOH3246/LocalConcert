package sparta.localconcert.domain.admin.service

import sparta.localconcert.domain.admin.dto.request.SignUpWithLoginRequest
import sparta.localconcert.domain.admin.dto.request.WithdrawRequest
import sparta.localconcert.domain.admin.dto.response.LoginResponse
import sparta.localconcert.domain.admin.dto.response.SignUpResponse

interface AdminService {

    fun signUp(email: String, request: SignUpWithLoginRequest): SignUpResponse

    fun login(request: SignUpWithLoginRequest): LoginResponse

    fun withdraw(email: String, request: WithdrawRequest)
}