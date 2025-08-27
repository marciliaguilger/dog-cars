package com.dog.cars.presentation.webhook.controller

import com.dog.cars.infrastructure.config.MercadoPagoProperties
import com.dog.cars.presentation.webhook.dto.WebhookNotification
import com.dog.cars.presentation.webhook.dto.WebhookData
import com.dog.cars.presentation.webhook.service.WebhookService
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
        @RequestHeader("x-signature") xSignature: String?,
        @RequestHeader("x-request-id") xRequestId: String?,
        @RequestParam("data.id") dataId: String?,
        @RequestBody payload: WebhookNotification
    ): ResponseEntity<String> {
        try {
            logger.info("Received webhook notification: type=${payload.type}, data.id=${dataId}, x-request-id=${xRequestId}")

            if (!mercadoPagoProperties.webhookSecret.isNullOrBlank()) {
                if (!verifySignature(xSignature, xRequestId, dataId)) {
                    logger.warn("HMAC signature verification failed for webhook")
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid signature")
                }
            }

            // Process the webhook notification
            webhookService.processNotification(payload)

            // Return 200 OK as required by Mercado Pago
            return ResponseEntity.ok("Webhook received successfully")

        } catch (e: Exception) {
            logger.error("Error processing webhook notification", e)
            // Return 200 OK even on error to prevent retries, but log the issue
            return ResponseEntity.ok("Webhook received (with errors)")
        }
    }

    private fun verifySignature(
        xSignature: String?,
        xRequestId: String?,
        dataId: String?
    ): Boolean {
        if (xSignature.isNullOrBlank() || xRequestId.isNullOrBlank() || dataId.isNullOrBlank()) {
            logger.warn("Missing required headers for signature verification")
            return false
        }

        try {
            // Parse x-signature header: "ts=1234567890,v1=abc123..."
            val signatureParts = xSignature.split(",").associate { part ->
                val keyValue = part.split("=", limit = 2)
                if (keyValue.size == 2) keyValue[0].trim() to keyValue[1].trim() else "" to ""
            }

            val timestamp = signatureParts["ts"] ?: return false
            val hash = signatureParts["v1"] ?: return false

            // Create manifest string as per Mercado Pago documentation
            val manifest = "id:$dataId;request-id:$xRequestId;ts:$timestamp;"

            // Generate HMAC-SHA256 signature
            val secret = mercadoPagoProperties.webhookSecret!!
            val mac = Mac.getInstance("HmacSHA256")
            val secretKey = SecretKeySpec(secret.toByteArray(), "HmacSHA256")
            mac.init(secretKey)

            val calculatedHash = mac.doFinal(manifest.toByteArray())
            val calculatedHashHex = calculatedHash.joinToString("") { "%02x".format(it) }

            val isValid = calculatedHashHex == hash
            if (!isValid) {
                logger.warn("Signature mismatch. Expected: $hash, Calculated: $calculatedHashHex")
            }

            return isValid

        } catch (e: NoSuchAlgorithmException) {
            logger.error("HMAC-SHA256 algorithm not available", e)
            return false
        } catch (e: InvalidKeyException) {
            logger.error("Invalid key for HMAC verification", e)
            return false
        } catch (e: Exception) {
            logger.error("Error during signature verification", e)
            return false
        }
    }
}
