package com.dog.cars.infrastructure.web.feign

import com.dog.cars.infrastructure.web.auth.TokenService
import feign.RequestInterceptor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FeignAuthConfig(
    private val tokenService: TokenService
) {

    @Bean
    fun mercadoPagoAuthInterceptor(): RequestInterceptor = RequestInterceptor { template ->
        val path = template.request().url()
        if (path.contains("/oauth/token")) {
            return@RequestInterceptor
        }
        val token = tokenService.getApplicationAccessToken()
        template.header("Authorization", "Bearer $token")
    }
}


