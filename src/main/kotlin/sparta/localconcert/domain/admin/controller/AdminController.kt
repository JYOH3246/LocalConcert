package sparta.localconcert.domain.admin.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import sparta.localconcert.domain.admin.dto.request.SignUpWithLoginRequest
import sparta.localconcert.domain.admin.dto.request.WithdrawRequest
import sparta.localconcert.domain.admin.dto.response.LoginResponse
import sparta.localconcert.domain.admin.dto.response.SignUpResponse
import sparta.localconcert.domain.admin.service.AdminService
import sparta.localconcert.global.security.UserPrincipal

@RestController
@RequestMapping("/api/admins")
class AdminController(
    private val adminService: AdminService
) {

    @PostMapping("/signup")
    fun signUp(@RequestBody request: SignUpWithLoginRequest): ResponseEntity<SignUpResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.signUp(request))
    }

    @PostMapping("/login")
    fun signIn(@RequestBody request: SignUpWithLoginRequest): ResponseEntity<LoginResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.login(request))
    }

    @DeleteMapping("/{adminId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun withdraw(
        @PathVariable adminId: Long,
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @RequestBody request: WithdrawRequest
    ): ResponseEntity<Unit> {
        adminService.withdraw(adminId, userPrincipal.email, request)

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

}