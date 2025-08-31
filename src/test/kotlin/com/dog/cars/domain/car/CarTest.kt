package com.dog.cars.domain.car

import com.dog.cars.domain.car.Car
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class CarTest {

    @Test
    fun `should create car with valid data`() {
        // Given
        val brand = "Toyota"
        val model = "Corolla"
        val year = 2022
        val color = "White"
        val price = BigDecimal("50000.00")

        // When
        val car = Car.create(brand, model, year, color, price, true)

        // Then
        assertEquals(brand, car.brand)
        assertEquals(model, car.model)
        assertEquals(year, car.year)
        assertEquals(color, car.color)
        assertEquals(price, car.price)
        assertTrue(car.available)
        assertNull(car.saleId)
    }

    @Test
    fun `should sell car when available`() {
        // Given
        val car = Car.create("Toyota", "Corolla", 2022, "White", BigDecimal("50000.00"), true)
        val saleId = UUID.randomUUID()
        val finalPrice = BigDecimal("45000.00")

        // When
        val soldCar = car.sell(saleId, finalPrice)

        // Then
        assertFalse(soldCar.available)
        assertEquals(saleId, soldCar.saleId)
        assertEquals(finalPrice, soldCar.price)
    }

    @Test
    fun `should throw exception when selling unavailable car`() {
        // Given
        val car = Car.create("Toyota", "Corolla", 2022, "White", BigDecimal("50000.00"), false)
        val saleId = UUID.randomUUID()
        val finalPrice = BigDecimal("45000.00")

        // When & Then
        val exception = assertThrows<IllegalStateException> {
            car.sell(saleId, finalPrice)
        }
        assertEquals("Car is already sold", exception.message)
    }

    @Test
    fun `should undo sale and make car available again`() {
        // Given
        val car = Car.create("Toyota", "Corolla", 2022, "White", BigDecimal("50000.00"), true)
        val saleId = UUID.randomUUID()
        val soldCar = car.sell(saleId, BigDecimal("45000.00"))

        // When
        val availableCar = soldCar.undoSale()

        // Then
        assertTrue(availableCar.available)
        assertNull(availableCar.saleId)
    }
}
