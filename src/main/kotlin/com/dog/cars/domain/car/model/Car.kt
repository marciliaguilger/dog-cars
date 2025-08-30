package com.dog.cars.domain.car.model

import java.math.BigDecimal
import java.util.*

data class Car (
        val id: UUID,
        val brand: String,
        val model: String,
        val year: Int,
        val color: String,
        val price: BigDecimal,
        val available: Boolean,
        val saleId: UUID?,
) {
    companion object {
        fun create(
            brand: String,
            model: String,
            year: Int,
            color: String,
            price: BigDecimal,
            available: Boolean
        ): Car {
            return Car(
                id = UUID.randomUUID(),
                brand = brand,
                model = model,
                year = year,
                color = color,
                price = price,
                available = available,
                saleId = null
            )
        }
    }


    fun sell(saleId: UUID?, finalPrice: BigDecimal): Car {
        if (!available) {
            throw IllegalStateException("Car is already sold")
        }

        return this.copy(available = false, saleId = saleId, price = finalPrice)
    }

    fun undoSale(): Car {
        return this.copy(available = true, saleId = null)
    }
}