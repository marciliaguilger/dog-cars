package com.dog.cars.application.usecase

import com.dog.cars.application.port.CarRepository
import com.dog.cars.application.port.PaymentRepository
import com.dog.cars.application.port.PersonRepository
import com.dog.cars.application.port.SaleRepository
import com.dog.cars.domain.car.Car
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
import kotlin.test.assertFalse

class SaleCarUseCaseTest {

    private lateinit var carRepository: CarRepository
    private lateinit var personRepository: PersonRepository
    private lateinit var saleRepository: SaleRepository
    private lateinit var paymentRepository: PaymentRepository
    private lateinit var saleCarUseCase: SaleCarUseCase

    @BeforeEach
    fun setUp() {
        carRepository = mockk()
        personRepository = mockk()
        saleRepository = mockk()
        paymentRepository = mockk()
        saleCarUseCase = SaleCarUseCase(carRepository, personRepository, saleRepository, paymentRepository)
    }

    @Test
    fun `should create sale successfully`() {
        // Given
        val carId = UUID.randomUUID()
        val buyerDocument = "12345678901"
        val saleDate = LocalDateTime.now()
        val discountAmount = BigDecimal("5000.00")
        
        val car = Car(carId, "Toyota", "Corolla", 2022, "White", BigDecimal("50000.00"), true, null)
        val person = Person(buyerDocument, "John Doe")
        
        every { carRepository.getById(carId) } returns car
        every { personRepository.getByDocument(buyerDocument) } returns person
        every { carRepository.upsert(any()) } returns Unit
        every { saleRepository.upsert(any()) } returns Unit

        // When
        val result = saleCarUseCase.sale(carId, buyerDocument, saleDate, discountAmount)

        // Then
        assertEquals(carId, result.carId)
        assertEquals(buyerDocument, result.customerDocument)
        assertEquals(BigDecimal("45000.00"), result.salePrice)
        assertEquals(discountAmount, result.discount)
        assertEquals(SaleStatus.CREATED, result.status)
        
        verify { carRepository.getById(carId) }
        verify { personRepository.getByDocument(buyerDocument) }
        verify { carRepository.upsert(any()) }
        verify { saleRepository.upsert(any()) }
    }

    @Test
    fun `should throw exception when car not found`() {
        // Given
        val carId = UUID.randomUUID()
        val buyerDocument = "12345678901"
        val saleDate = LocalDateTime.now()
        val discountAmount = BigDecimal("5000.00")
        
        every { carRepository.getById(carId) } returns null

        // When & Then
        val exception = assertThrows<IllegalArgumentException> {
            saleCarUseCase.sale(carId, buyerDocument, saleDate, discountAmount)
        }
        assertEquals("Car with id $carId not found", exception.message)
    }

    @Test
    fun `should throw exception when car is already sold`() {
        // Given
        val carId = UUID.randomUUID()
        val buyerDocument = "12345678901"
        val saleDate = LocalDateTime.now()
        val discountAmount = BigDecimal("5000.00")
        
        val soldCar = Car(carId, "Toyota", "Corolla", 2022, "White", BigDecimal("50000.00"), false, UUID.randomUUID())
        
        every { carRepository.getById(carId) } returns soldCar

        // When & Then
        val exception = assertThrows<IllegalStateException> {
            saleCarUseCase.sale(carId, buyerDocument, saleDate, discountAmount)
        }
        assertEquals("Car with id $carId is already sold", exception.message)
    }

    @Test
    fun `should throw exception when buyer not found`() {
        // Given
        val carId = UUID.randomUUID()
        val buyerDocument = "12345678901"
        val saleDate = LocalDateTime.now()
        val discountAmount = BigDecimal("5000.00")
        
        val car = Car(carId, "Toyota", "Corolla", 2022, "White", BigDecimal("50000.00"), true, null)
        
        every { carRepository.getById(carId) } returns car
        every { personRepository.getByDocument(buyerDocument) } returns null

        // When & Then
        val exception = assertThrows<IllegalArgumentException> {
            saleCarUseCase.sale(carId, buyerDocument, saleDate, discountAmount)
        }
        assertEquals("Buyer with document $buyerDocument not found", exception.message)
    }

    @Test
    fun `should get all sales with payments`() {
        // Given
        val saleId = UUID.randomUUID()
        val sale = Sale(saleId, UUID.randomUUID(), "12345678901", LocalDateTime.now(), BigDecimal("45000.00"), BigDecimal("5000.00"), SaleStatus.CREATED)
        
        every { saleRepository.findAll() } returns listOf(sale)
        every { paymentRepository.getBySaleId(saleId) } returns null

        // When
        val result = saleCarUseCase.getAll()

        // Then
        assertEquals(1, result.size)
        assertEquals(sale.copy(payment = null), result.first())
        verify { saleRepository.findAll() }
        verify { paymentRepository.getBySaleId(saleId) }
    }

    @Test
    fun `should cancel sale successfully`() {
        // Given
        val saleId = UUID.randomUUID()
        val carId = UUID.randomUUID()
        val reason = "Customer request"
        
        val sale = Sale(saleId, carId, "12345678901", LocalDateTime.now(), BigDecimal("45000.00"), BigDecimal("5000.00"), SaleStatus.CREATED)
        val car = Car(carId, "Toyota", "Corolla", 2022, "White", BigDecimal("50000.00"), false, saleId)
        
        every { saleRepository.findById(saleId) } returns sale
        every { carRepository.getById(carId) } returns car
        every { carRepository.upsert(any()) } returns Unit
        every { saleRepository.upsert(any()) } returns Unit

        // When
        saleCarUseCase.cancel(saleId, reason)

        // Then
        verify { saleRepository.findById(saleId) }
        verify { carRepository.getById(carId) }
        verify { carRepository.upsert(any()) }
        verify { saleRepository.upsert(any()) }
    }
}
