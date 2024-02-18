package sparta.localconcert.domain.admin.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import sparta.localconcert.domain.admin.dto.request.SignUpWithLoginRequest
import sparta.localconcert.domain.admin.dto.response.LoginResponse
import sparta.localconcert.domain.admin.dto.response.SignUpResponse
import sparta.localconcert.domain.admin.service.AdminService

@RestController
@RequestMapping("/api/admins")
class AdminController(
    private val adminService: AdminService
) {

    @PostMapping("/signup")
    fun signUp(@RequestBody request: SignUpWithLoginRequest, email: String): ResponseEntity<SignUpResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.signUp(email, request))
    }

    @PostMapping("/login")
    fun signIn(@RequestBody request: SignUpWithLoginRequest): ResponseEntity<LoginResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.login(request))
    }

}