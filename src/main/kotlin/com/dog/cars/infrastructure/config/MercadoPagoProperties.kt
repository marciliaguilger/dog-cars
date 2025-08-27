package com.dog.cars.infrastructure.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "mercadopago")
data class MercadoPagoProperties(
    var clientId: String = "",
    var clientSecret: String = "",
    var redirectUri: String = "",
    var collectorId: Long? = null,
    var posExternalId: String? = null,
    var baseUrl: String = "https://api.mercadopago.com",
    var webhookSecret: String? = null,
    var accessToken: String? = null
)


