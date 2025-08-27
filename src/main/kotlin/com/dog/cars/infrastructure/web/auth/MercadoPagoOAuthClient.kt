package com.dog.cars.infrastructure.web.auth

import com.dog.cars.infrastructure.web.auth.dto.OAuthTokenResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "mercadoPagoOAuthClient", url = "\${mercadopago.baseUrl}")
interface MercadoPagoOAuthClient {
    @PostMapping(
        value = ["/oauth/token"],
        consumes = ["application/x-www-form-urlencoded"],
        produces = ["application/json"]
    )
    fun getAccessTokenWithClientCredentials(
        @RequestHeader("Content-Type") contentType: String = "application/x-www-form-urlencoded",
        @RequestParam("client_id") clientId: String,
        @RequestParam("client_secret") clientSecret: String,
        @RequestParam("grant_type") grantType: String = "client_credentials"
    ): OAuthTokenResponse
}