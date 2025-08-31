package com.dog.cars.application.usecase

import com.dog.cars.application.port.CarRepository
import com.dog.cars.application.port.PaymentRepository
import com.dog.cars.application.port.PersonRepository
import com.dog.cars.application.port.SaleRepository
import com.dog.cars.application.service.PaymentService
import com.dog.cars.domain.car.Car
import com.dog.cars.domain.payments.Payment
import com.dog.cars.domain.payments.PaymentStatus
import com.dog.cars.domain.payments.PaymentType
import com.dog.cars.domain.payments.PixQrCodePayment
import com.dog.cars.domain.person.Person
import com.dog.cars.domain.sales.Sale
import com.dog.cars.domain.sales.enum.SaleStatus
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*
import kotlin.test.assertEquals

class PaymentUseCaseTest {

    private lateinit var personRepository: PersonRepository
    private lateinit var saleRepository: SaleRepository
    private lateinit var paymentRepository: PaymentRepository
    private lateinit var paymentService: PaymentService
    private lateinit var carRepository: CarRepository
    private lateinit var paymentUseCase: PaymentUseCase

    @BeforeEach
    fun setUp() {
        personRepository = mockk()
        saleRepository = mockk()
        paymentRepository = mockk()
        paymentService = mockk()
        carRepository = mockk()
        paymentUseCase = PaymentUseCase(personRepository, saleRepository, paymentRepository, paymentService, carRepository)
    }

    @Test
    fun `should create payment successfully`() {
        // Given
        val saleId = UUID.randomUUID()
        val customerId = UUID.randomUUID()
        val carId = UUID.randomUUID()
        val customerDocument = "12345678901"
        
        val sale = Sale(saleId, carId, customerDocument, LocalDateTime.now(), BigDecimal("45000.00"), BigDecimal("5000.00"), SaleStatus.CREATED)
        val customer = Person(customerDocument, "John Doe")
        val pixQrCode = PixQrCodePayment("qr-code-data", LocalDateTime.now(), "PENDING", "qr", BigDecimal("45000.00"), saleId)
        
        every { saleRepository.findById(saleId) } returns sale
        every { personRepository.getByDocument(customerDocument) } returns customer
        every { paymentService.generateQrCode(any()) } returns pixQrCode
        every { saleRepository.upsert(any()) } returns Unit
        every { paymentRepository.upsert(any()) } returns Unit

        // When
        val result = paymentUseCase.createPayment(saleId, PaymentType.PIX)

        // Then
        assertEquals(BigDecimal("45000.00"), result.totalAmount)
        assertEquals(PaymentStatus.CREATED, result.status)
        assertEquals(saleId, result.saleId)
        assertEquals(PaymentType.PIX, result.type)
        assertEquals("qr-code-data", result.qrCode)
        
        verify { saleRepository.findById(saleId) }
        verify { personRepository.getByDocument(customerDocument) }
        verify { paymentService.generateQrCode(any()) }
        verify { saleRepository.upsert(any()) }
        verify { paymentRepository.upsert(any()) }
    }

    @Test
    fun `should throw exception when sale is not in CREATED status`() {
        // Given
        val saleId = UUID.randomUUID()
        val sale = Sale(saleId, UUID.randomUUID(), "12345678901", LocalDateTime.now(), BigDecimal("45000.00"), BigDecimal("5000.00"), SaleStatus.APPROVED)
        
        every { saleRepository.findById(saleId) } returns sale

        // When & Then
        val exception = assertThrows<IllegalStateException> {
            paymentUseCase.createPayment(saleId, PaymentType.PIX)
        }
        assertEquals("Sale with id $saleId is not in a valid state for payment", exception.message)
    }

    @Test
    fun `should confirm payment successfully`() {
        // Given
        val paymentId = UUID.randomUUID()
        val saleId = UUID.randomUUID()
        
        val payment = Payment(paymentId, BigDecimal("45000.00"), PaymentStatus.CREATED, "Payment description", saleId, PaymentType.PIX, "qr-code", "12345678901", emptyList())
        val sale = Sale(saleId, UUID.randomUUID(), "12345678901", LocalDateTime.now(), BigDecimal("45000.00"), BigDecimal("5000.00"), SaleStatus.WAITING_PAYMENT)
        
        every { paymentRepository.getById(paymentId) } returns payment
        every { saleRepository.findById(saleId) } returns sale
        every { saleRepository.upsert(any()) } returns Unit
        every { paymentRepository.upsert(any()) } returns Unit

        // When
        paymentUseCase.confirmPayment(paymentId)

        // Then
        verify { paymentRepository.getById(paymentId) }
        verify { saleRepository.findById(saleId) }
        verify { saleRepository.upsert(any()) }
        verify { paymentRepository.upsert(any()) }
    }

    @Test
    fun `should reject payment successfully`() {
        // Given
        val paymentId = UUID.randomUUID()
        val payment = Payment(paymentId, BigDecimal("45000.00"), PaymentStatus.CREATED, "Payment description", UUID.randomUUID(), PaymentType.PIX, "qr-code", "12345678901", emptyList())
        
        every { paymentRepository.getById(paymentId) } returns payment
        every { paymentRepository.upsert(any()) } returns Unit

        // When
        paymentUseCase.rejectPayment(paymentId)

        // Then
        verify { paymentRepository.getById(paymentId) }
        verify { paymentRepository.upsert(any()) }
    }

    @Test
    fun `should cancel payment and undo car sale`() {
        // Given
        val paymentId = UUID.randomUUID()
        val saleId = UUID.randomUUID()
        val carId = UUID.randomUUID()
        
        val payment = Payment(paymentId, BigDecimal("45000.00"), PaymentStatus.CREATED, "Payment description", saleId, PaymentType.PIX, "qr-code", "12345678901", emptyList())
        val sale = Sale(saleId, carId, "12345678901", LocalDateTime.now(), BigDecimal("45000.00"), BigDecimal("5000.00"), SaleStatus.WAITING_PAYMENT)
        val car = Car(carId, "Toyota", "Corolla", 2022, "White", BigDecimal("50000.00"), false, saleId)
        
        every { paymentRepository.getById(paymentId) } returns payment
        every { saleRepository.findById(saleId) } returns sale
        every { carRepository.getById(carId) } returns car
        every { paymentRepository.upsert(any()) } returns Unit
        every { saleRepository.upsert(any()) } returns Unit
        every { carRepository.upsert(any()) } returns Unit

        // When
        paymentUseCase.cancelPayment(paymentId)

        // Then
        verify { paymentRepository.getById(paymentId) }
        verify { saleRepository.findById(saleId) }
        verify { carRepository.getById(carId) }
        verify { paymentRepository.upsert(any()) }
        verify { saleRepository.upsert(any()) }
        verify { carRepository.upsert(any()) }
    }

    @Test
    fun `should throw exception when payment not found for confirmation`() {
        // Given
        val paymentId = UUID.randomUUID()
        every { paymentRepository.getById(paymentId) } returns null

        // When & Then
        val exception = assertThrows<IllegalArgumentException> {
            paymentUseCase.confirmPayment(paymentId)
        }
        assertEquals("Payment with id $paymentId not found", exception.message)
    }
}
