package com.dog.cars.infrastructure.web.auth.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class OAuthTokenResponse(
    @JsonProperty("access_token") val accessToken: String,
    @JsonProperty("token_type") val tokenType: String,
    @JsonProperty("expires_in") val expiresInSeconds: Long,
    @JsonProperty("scope") val scope: String?,
    @JsonProperty("user_id") val userId: Long?,
    @JsonProperty("refresh_token") val refreshToken: String?,
    @JsonProperty("public_key") val publicKey: String?,
    @JsonProperty("live_mode") val liveMode: Boolean?
)


