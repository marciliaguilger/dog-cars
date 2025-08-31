package com.dog.cars.application.usecase

import com.dog.cars.application.port.CarRepository
import com.dog.cars.domain.car.Car
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.util.*
import kotlin.test.assertEquals

class ManageCarUseCaseTest {

    private lateinit var carRepository: CarRepository
    private lateinit var manageCarUseCase: ManageCarUseCase

    @BeforeEach
    fun setUp() {
        carRepository = mockk()
        manageCarUseCase = ManageCarUseCase(carRepository)
    }

    @Test
    fun `should execute and save car`() {
        // Given
        val car = Car.create("Toyota", "Corolla", 2022, "White", BigDecimal("50000.00"), true)
        every { carRepository.upsert(car) } returns Unit

        // When
        val result = manageCarUseCase.execute(car)

        // Then
        assertEquals(car, result)
        verify { carRepository.upsert(car) }
    }

    @Test
    fun `should get available cars sorted by price`() {
        // Given
        val car1 = Car(UUID.randomUUID(), "Toyota", "Corolla", 2022, "White", BigDecimal("60000.00"), true, null)
        val car2 = Car(UUID.randomUUID(), "Honda", "Civic", 2021, "Black", BigDecimal("45000.00"), true, null)
        val car3 = Car(UUID.randomUUID(), "Ford", "Focus", 2020, "Blue", BigDecimal("55000.00"), true, null)
        
        val cars = listOf(car1, car2, car3)
        every { carRepository.getAll(true) } returns cars

        // When
        val result = manageCarUseCase.getCars(true)

        // Then
        val sortedCars = result.toList()
        assertEquals(3, sortedCars.size)
        assertEquals(car2, sortedCars[0]) // Cheapest first
        assertEquals(car3, sortedCars[1])
        assertEquals(car1, sortedCars[2]) // Most expensive last
        verify { carRepository.getAll(true) }
    }

    @Test
    fun `should get unavailable cars when available is false`() {
        // Given
        val soldCar = Car(UUID.randomUUID(), "Toyota", "Corolla", 2022, "White", BigDecimal("50000.00"), false, UUID.randomUUID())
        every { carRepository.getAll(false) } returns listOf(soldCar)

        // When
        val result = manageCarUseCase.getCars(false)

        // Then
        assertEquals(1, result.size)
        assertEquals(soldCar, result.first())
        verify { carRepository.getAll(false) }
    }

    @Test
    fun `should return empty list when no cars available`() {
        // Given
        every { carRepository.getAll(true) } returns emptyList()

        // When
        val result = manageCarUseCase.getCars(true)

        // Then
        assertEquals(0, result.size)
        verify { carRepository.getAll(true) }
    }
}
