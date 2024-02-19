package sparta.localconcert.global.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import sparta.localconcert.global.security.jwt.JwtAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtAuthenticationFilter: JwtAuthenticationFilter,
    private val authenticationEntryPoint: CustomAuthenticationEntrypoint,
    private val accessDeniedHandler: AccessDeniedHandler
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .httpBasic { it.disable() }
            .formLogin { it.disable() }
            .csrf { it.disable() }
            .authorizeHttpRequests {

                it.requestMatchers(AntPathRequestMatcher("/api/admins")).permitAll()
                it.requestMatchers(AntPathRequestMatcher("/api/admins/signup")).permitAll()
                it.requestMatchers(AntPathRequestMatcher("/api/admins/login")).permitAll()
                it.requestMatchers(AntPathRequestMatcher("/swagger-ui/**")).permitAll()
                it.requestMatchers(AntPathRequestMatcher("/v3/api-docs/**")).permitAll()
                    .anyRequest().authenticated()
            }
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .exceptionHandling {
                it.authenticationEntryPoint(authenticationEntryPoint)
                it.accessDeniedHandler(accessDeniedHandler)
            }
            .build()
    }
}