package com.dog.cars.application.service

import com.dog.cars.application.usecase.PaymentUseCase
import com.dog.cars.presentation.webhook.dto.WebhookData
import com.dog.cars.presentation.webhook.dto.WebhookNotification
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

class WebhookServiceTest {

    private lateinit var paymentUseCase: PaymentUseCase
    private lateinit var webhookService: WebhookService

    @BeforeEach
    fun setUp() {
        paymentUseCase = mockk()
        webhookService = WebhookService(paymentUseCase)
    }

    @Test
    fun `should process payment notification with approved status`() {
        // Given
        val paymentId = UUID.randomUUID()
        val notification = WebhookNotification(
            type = "payment",
            data = WebhookData(id = paymentId.toString(), situation = "approved")
        )
        
        every { paymentUseCase.confirmPayment(paymentId) } returns Unit

        // When
        webhookService.processNotification(notification)

        // Then
        verify { paymentUseCase.confirmPayment(paymentId) }
    }

    @Test
    fun `should process payment notification with rejected status`() {
        // Given
        val paymentId = UUID.randomUUID()
        val notification = WebhookNotification(
            type = "payment",
            data = WebhookData(id = paymentId.toString(), situation = "rejected")
        )
        
        every { paymentUseCase.rejectPayment(paymentId) } returns Unit

        // When
        webhookService.processNotification(notification)

        // Then
        verify { paymentUseCase.rejectPayment(paymentId) }
    }

    @Test
    fun `should process payment notification with cancelled status`() {
        // Given
        val paymentId = UUID.randomUUID()
        val notification = WebhookNotification(
            type = "payment",
            data = WebhookData(id = paymentId.toString(), situation = "cancelled")
        )
        
        every { paymentUseCase.rejectPayment(paymentId) } returns Unit

        // When
        webhookService.processNotification(notification)

        // Then
        verify { paymentUseCase.rejectPayment(paymentId) }
    }

    @Test
    fun `should ignore unknown payment status`() {
        // Given
        val paymentId = UUID.randomUUID()
        val notification = WebhookNotification(
            type = "payment",
            data = WebhookData(id = paymentId.toString(), situation = "pending")
        )

        // When
        webhookService.processNotification(notification)

        // Then
        verify(exactly = 0) { paymentUseCase.confirmPayment(any()) }
        verify(exactly = 0) { paymentUseCase.rejectPayment(any()) }
    }

    @Test
    fun `should ignore unknown notification type`() {
        // Given
        val notification = WebhookNotification(
            type = "unknown",
            data = WebhookData(id = UUID.randomUUID().toString(), situation = "approved")
        )

        // When
        webhookService.processNotification(notification)

        // Then
        verify(exactly = 0) { paymentUseCase.confirmPayment(any()) }
        verify(exactly = 0) { paymentUseCase.rejectPayment(any()) }
    }

    @Test
    fun `should handle invalid UUID in payment ID`() {
        // Given
        val notification = WebhookNotification(
            type = "payment",
            data = WebhookData(id = "invalid-uuid", situation = "approved")
        )

        // When & Then
        assertThrows<IllegalArgumentException> {
            webhookService.processNotification(notification)
        }
        
        verify(exactly = 0) { paymentUseCase.confirmPayment(any()) }
    }

    @Test
    fun `should propagate exception from payment use case`() {
        // Given
        val paymentId = UUID.randomUUID()
        val notification = WebhookNotification(
            type = "payment",
            data = WebhookData(id = paymentId.toString(), situation = "approved")
        )
        
        every { paymentUseCase.confirmPayment(paymentId) } throws RuntimeException("Payment processing failed")

        // When & Then
        assertThrows<RuntimeException> {
            webhookService.processNotification(notification)
        }
        
        verify { paymentUseCase.confirmPayment(paymentId) }
    }
}
