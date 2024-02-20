package sparta.localconcert.global.security.jwt

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import sparta.localconcert.global.security.UserPrincipal
import java.io.Serializable

class JwtAuthenticationToken(
    private val principal: UserPrincipal,
    authorities: Collection<GrantedAuthority>
) : AbstractAuthenticationToken(authorities), Serializable {

    init {
        super.setAuthenticated(true)
    }

    override fun getCredentials() = null

    override fun getPrincipal() = principal

    override fun isAuthenticated(): Boolean {
        return true
    }
}
