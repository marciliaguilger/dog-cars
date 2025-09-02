package com.dog.cars.presentation.webhook.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class WebhookNotification(
    @JsonProperty("type") val type: NotificationType,
    @JsonProperty("data") val data: WebhookData
)

data class WebhookData(
    @JsonProperty("id") val id: String,
    @JsonProperty("situation") val situation: Situation
)

enum class NotificationType(val type: String) {
    PAYMENT("payment");
}

enum class Situation(val situation: String) {
    APPROVED("approved"),
    PENDING("pending"),
    REJECTED("rejected"),
    CANCELLED("cancelled");
}