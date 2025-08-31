package com.dog.cars.presentation.webhook.service

import com.dog.cars.domain.payments.usecase.PaymentUseCase
import com.dog.cars.presentation.webhook.dto.WebhookNotification
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.*

@Service
class WebhookService(
    private val paymentUseCase: PaymentUseCase
) {
    private val logger = LoggerFactory.getLogger(WebhookService::class.java)

    fun processNotification(notification: WebhookNotification) {
        logger.info("Processing webhook notification: type=${notification.type}, id=${notification.data.id}")

        when (notification.type.name.lowercase()) {
            "payment" -> handlePaymentNotification(notification.data.id, notification.data.situation.name)
            else -> {
                logger.warn("Unknown webhook notification type: ${notification.type}")
                throw Exception("Unhandled webhook type: ${notification.type}")
            }
        }
    }

    private fun handlePaymentNotification(paymentId: String, situation: String) {
        logger.info("Processing payment notification for payment ID: $paymentId")
        // TODO: Implement payment processing logic

        when (situation.lowercase()) {
            "approved" -> {
                logger.info("Payment $paymentId approved. Confirming payment.")
                paymentUseCase.confirmPayment(UUID.fromString(paymentId))
            }
            "pending" -> {
                logger.info("Payment $paymentId is pending. No action taken.")
            }
            "rejected" -> {
                logger.warn("Payment $paymentId was rejected. Taking appropriate action.")
                paymentUseCase.rejectPayment(UUID.fromString(paymentId))
            }
            "cancelled" -> {
                logger.warn("Payment $paymentId was cancelled. Taking appropriate action.")
                paymentUseCase.cancelPayment(UUID.fromString(paymentId))
            }
            else -> {
               throw Exception("Unhandled payment situation: $situation for payment ID: $paymentId")
            }
        }
    }


}
