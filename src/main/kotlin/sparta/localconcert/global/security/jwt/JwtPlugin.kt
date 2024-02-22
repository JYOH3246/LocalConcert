package sparta.localconcert.global.security.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import sparta.localconcert.domain.admin.repository.AdminRefreshTokenRepository
import java.nio.charset.StandardCharsets
import java.time.Duration
import java.time.Instant
import java.util.*

@Component
class JwtPlugin(
    @Value("\${auth.jwt.issuer}") private val issuer: String,
    @Value("\${auth.jwt.secret}") private val secret: String,
    @Value("\${auth.jwt.accessTokenExpirationHour}") private val accessTokenExpirationHour: Long,
    @Value("\${auth.jwt.refreshTokenExpirationHour}") private val refreshTokenExpirationHour: Long,

    private val adminRefreshTokenRepository: AdminRefreshTokenRepository
) {

    private val reissueLimit = refreshTokenExpirationHour * 60 / accessTokenExpirationHour

    fun validateToken(jwt: String): Result<Jws<Claims>> {
        return kotlin.runCatching {
            val key = Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8))
            Jwts.parser().verifyWith(key).build().parseSignedClaims(jwt)
        }
    }

    fun refrechAccessToken(adminId: Long, email: String): String {
        return generateRefreshToken(adminId, email, Duration.ofHours(refreshTokenExpirationHour))
    }

    fun generateRefreshToken(adminId: Long, email: String, expirationPeriod: Duration): String {

        val key = Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8))
        val now = Instant.now()

        return Jwts.builder()
            .issuer(issuer)
            .issuedAt(Date.from(Instant.now()))
            .expiration(Date.from(now.plus(expirationPeriod)))
            .signWith(key)
            .compact()
    }

    fun generateAccessToken(adminId: Long, email: String): String {
        return generateToken(adminId, email, Duration.ofHours(accessTokenExpirationHour))
    }

    private fun generateToken(adminId: Long, email: String, expirationPeriod: Duration): String {
        val claims: Claims = Jwts.claims()
            .add(mapOf("email" to email))
            .build()

        val key = Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8))
        val now = Instant.now()

        return Jwts.builder()
            .subject(adminId.toString())
            .issuer(issuer)
            .issuedAt(Date.from(Instant.now()))
            .expiration(Date.from(now.plus(expirationPeriod)))
            .claims(claims)
            .signWith(key)
            .compact()
    }
}