package com.dog.cars.application.service

import com.dog.cars.application.port.PaymentGateway
import com.dog.cars.domain.payments.Payment
import com.dog.cars.domain.payments.PaymentItem
import com.dog.cars.domain.payments.PaymentStatus
import com.dog.cars.domain.payments.PaymentType
import com.dog.cars.domain.payments.PixQrCodePayment
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*
import kotlin.test.assertEquals

class PaymentServiceTest {

    private lateinit var paymentGateway: PaymentGateway
    private lateinit var paymentService: PaymentService

    @BeforeEach
    fun setUp() {
        paymentGateway = mockk()
        paymentService = PaymentService(paymentGateway)
    }

    @Test
    fun `should generate QR code successfully`() {
        // Given
        val paymentId = UUID.randomUUID()
        val saleId = UUID.randomUUID()
        val carId = UUID.randomUUID()
        
        val paymentItem = PaymentItem(carId, "Toyota Corolla 2022", BigDecimal("45000.00"), 1)
        val payment = Payment(
            id = paymentId,
            totalAmount = BigDecimal("45000.00"),
            status = PaymentStatus.CREATED,
            description = "Payment for car sale",
            saleId = saleId,
            type = PaymentType.PIX,
            qrCode = "",
            customerDocument = "12345678901",
            items = listOf(paymentItem)
        )
        
        val expectedPixQrCode = PixQrCodePayment(
            pixKey = "00020126360014BR.GOV.BCB.PIX0114+55219999999952040000530398654041.005802BR5925Nome do Recebedor6009RIO DE JANEIRO61080540900062070503***63041D3D",
            createdDate = LocalDateTime.now(),
            status = "PENDING",
            type = "qr",
            totalAmount = BigDecimal("45000.00"),
            externalSaleId = saleId
        )
        
        every { paymentGateway.pixPaymentGenerateQrCode(payment) } returns expectedPixQrCode

        // When
        val result = paymentService.generateQrCode(payment)

        // Then
        assertEquals(expectedPixQrCode.pixKey, result.pixKey)
        assertEquals(expectedPixQrCode.status, result.status)
        assertEquals(expectedPixQrCode.type, result.type)
        assertEquals(expectedPixQrCode.totalAmount, result.totalAmount)
        assertEquals(expectedPixQrCode.externalSaleId, result.externalSaleId)
        
        verify { paymentGateway.pixPaymentGenerateQrCode(payment) }
    }

    @Test
    fun `should delegate QR code generation to payment gateway`() {
        // Given
        val payment = Payment(
            id = UUID.randomUUID(),
            totalAmount = BigDecimal("100.00"),
            status = PaymentStatus.CREATED,
            description = "Test payment",
            saleId = UUID.randomUUID(),
            type = PaymentType.PIX,
            qrCode = "",
            customerDocument = "12345678901",
            items = emptyList()
        )
        
        val pixQrCode = PixQrCodePayment(
            pixKey = "test-qr-code",
            createdDate = LocalDateTime.now(),
            status = "PENDING",
            type = "qr",
            totalAmount = BigDecimal("100.00"),
            externalSaleId = payment.saleId
        )
        
        every { paymentGateway.pixPaymentGenerateQrCode(payment) } returns pixQrCode

        // When
        val result = paymentService.generateQrCode(payment)

        // Then
        assertEquals(pixQrCode, result)
        verify(exactly = 1) { paymentGateway.pixPaymentGenerateQrCode(payment) }
    }
}
