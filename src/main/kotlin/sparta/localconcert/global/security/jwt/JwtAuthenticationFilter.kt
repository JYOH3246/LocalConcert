package sparta.localconcert.global.security.jwt

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import sparta.localconcert.domain.admin.model.AdminRefreshToken
import sparta.localconcert.global.security.UserPrincipal

@Component
class JwtAuthenticationFilter(
    private val jwtPlugin: JwtPlugin
) : OncePerRequestFilter() {

    companion object {
        private val BEARER_PATTERN = Regex("^Bearer (.+?)$")
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val jwt = request.getBearerToken()

        if (jwt != null) {
            jwtPlugin.validateToken(jwt)
                .onSuccess {
                    val adminId = it.payload.subject.toLong()
                    val email = it.payload.get("email", String::class.java)

                    val principal = UserPrincipal(
                        adminId = adminId,
                        email = email
                    )

                    // enum 이 따로 없어서 권한 부여위해 직접 추가
                    val authorities: Collection<GrantedAuthority> = listOf(SimpleGrantedAuthority("ROLE_ADMIN"))

                    val authentication = JwtAuthenticationToken(
                        principal = principal,
                        authorities = authorities
                    )
                    SecurityContextHolder.getContext().authentication = authentication

                }
        }
        filterChain.doFilter(request, response)
    }

    private fun HttpServletRequest.getBearerToken(): String? {
        val headerValue = this.getHeader(HttpHeaders.AUTHORIZATION) ?: return null // Bearer {JWT}
        return BEARER_PATTERN.find(headerValue)?.groupValues?.get(1)

    }
}
