package com.dog.cars.presentation.webhook.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class WebhookNotification(
    @JsonProperty("type") val type: String,
    @JsonProperty("data") val data: WebhookData
)

data class WebhookData(
    @JsonProperty("id") val id: String,
    @JsonProperty("situation") val situation: String
)
