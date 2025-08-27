package com.dog.cars.infrastructure.web.auth

import com.dog.cars.infrastructure.config.MercadoPagoProperties
import com.dog.cars.infrastructure.web.auth.dto.OAuthTokenResponse
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.concurrent.atomic.AtomicReference

@Service
class TokenService(
    private val oAuthClient: MercadoPagoOAuthClient,
    private val properties: MercadoPagoProperties
) {

    private val cachedToken: AtomicReference<CachedToken?> = AtomicReference(null)

    fun getApplicationAccessToken(): String {
        if (properties.accessToken?.startsWith("TEST-") == true) {
            return properties.accessToken!!
        }

        val now = Instant.now()
        val current = cachedToken.get()
        if (current != null && now.isBefore(current.expiresAt)) {
            return current.accessToken
        }

        val response: OAuthTokenResponse = oAuthClient.getAccessTokenWithClientCredentials(
            clientId = properties.clientId,
            clientSecret = properties.clientSecret
        )

        val safetyMarginSeconds = 60L
        val expiresAt = now.plusSeconds(maxOf(0, response.expiresInSeconds - safetyMarginSeconds))
        val newToken = CachedToken(response.accessToken, expiresAt)
        cachedToken.set(newToken)
        return newToken.accessToken
    }

    private data class CachedToken(
        val accessToken: String,
        val expiresAt: Instant
    )
}


