package com.dog.cars.presentation.webhook.controller

import com.dog.cars.infrastructure.config.MercadoPagoProperties
import com.dog.cars.presentation.webhook.dto.WebhookNotification
import com.dog.cars.application.service.WebhookService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException

@RestController
@RequestMapping("/webhooks")
class WebhookController(
    private val webhookService: WebhookService,
    private val mercadoPagoProperties: MercadoPagoProperties
) {
    private val logger = LoggerFactory.getLogger(WebhookController::class.java)

    @PostMapping("/mercadopago")
    fun handleMercadoPagoWebhook(
        @RequestBody body: WebhookNotification
    ): ResponseEntity<String> {
        try {
            logger.info("Received webhook notification: type=${body.type}, data.id=${body.data.id}")

            webhookService.processNotification(body)

            return ResponseEntity.ok("Webhook received successfully")

        } catch (e: Exception) {
            logger.error("Error processing webhook notification", e)
            return ResponseEntity.ok("Webhook received (with errors)")
        }
    }
}
