package com.dog.cars.domain.sales

import com.dog.cars.domain.sales.Sale
import com.dog.cars.domain.sales.enum.SaleStatus
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNull

class SaleTest {

    @Test
    fun `should create sale with correct properties`() {
        // Given
        val id = UUID.randomUUID()
        val carId = UUID.randomUUID()
        val customerDocument = "12345678901"
        val saleDate = LocalDateTime.now()
        val salePrice = BigDecimal("45000.00")
        val discount = BigDecimal("5000.00")
        val status = SaleStatus.CREATED

        // When
        val sale = Sale(id, carId, customerDocument, saleDate, salePrice, discount, status)

        // Then
        assertEquals(id, sale.id)
        assertEquals(carId, sale.carId)
        assertEquals(customerDocument, sale.customerDocument)
        assertEquals(saleDate, sale.saleDate)
        assertEquals(salePrice, sale.salePrice)
        assertEquals(discount, sale.discount)
        assertEquals(status, sale.status)
        assertNull(sale.payment)
        assertNull(sale.cancellationReason)
    }

    @Test
    fun `should copy sale with new status`() {
        // Given
        val originalSale = Sale(
            id = UUID.randomUUID(),
            carId = UUID.randomUUID(),
            customerDocument = "12345678901",
            saleDate = LocalDateTime.now(),
            salePrice = BigDecimal("45000.00"),
            discount = BigDecimal("5000.00"),
            status = SaleStatus.CREATED
        )

        // When
        val updatedSale = originalSale.copy(status = SaleStatus.APPROVED)

        // Then
        assertEquals(originalSale.id, updatedSale.id)
        assertEquals(originalSale.carId, updatedSale.carId)
        assertEquals(originalSale.customerDocument, updatedSale.customerDocument)
        assertEquals(SaleStatus.APPROVED, updatedSale.status)
    }

    @Test
    fun `should copy sale with cancellation reason`() {
        // Given
        val originalSale = Sale(
            id = UUID.randomUUID(),
            carId = UUID.randomUUID(),
            customerDocument = "12345678901",
            saleDate = LocalDateTime.now(),
            salePrice = BigDecimal("45000.00"),
            discount = BigDecimal("5000.00"),
            status = SaleStatus.CREATED
        )
        val reason = "Customer request"

        // When
        val cancelledSale = originalSale.copy(
            status = SaleStatus.CANCELLED,
            cancellationReason = reason
        )

        // Then
        assertEquals(SaleStatus.CANCELLED, cancelledSale.status)
        assertEquals(reason, cancelledSale.cancellationReason)
    }
}
