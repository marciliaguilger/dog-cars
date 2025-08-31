package com.dog.cars.application.service

import com.dog.cars.application.usecase.PaymentUseCase
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

        when (notification.type) {
            "payment" -> {
                logger.info("Payment updated: ${notification.data.id}")
                handlePaymentUpdate(notification)
            }
            else -> {
                logger.warn("Unknown notification type: ${notification.type}")
            }
        }
    }

    private fun handlePaymentUpdate(notification: WebhookNotification) {
        try {
            val paymentId = UUID.fromString(notification.data.id)
            
            when (notification.data.situation) {
                "approved" -> {
                    paymentUseCase.confirmPayment(paymentId)
                    logger.info("Payment confirmed: $paymentId")
                }
                "rejected", "cancelled" -> {
                    paymentUseCase.rejectPayment(paymentId)
                    logger.info("Payment rejected: $paymentId")
                }
                else -> {
                    logger.info("Payment status unchanged: ${notification.data.situation}")
                }
            }
        } catch (e: Exception) {
            logger.error("Error processing payment update: ${e.message}", e)
            throw e
        }
    }
}
