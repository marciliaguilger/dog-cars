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

    fun update(
        brand: String? = null,
        model: String? = null,
        year: Int? = null,
        color: String? = null,
        price: BigDecimal? = null,
        available: Boolean? = null
    ): Car {
        return Car(
            id = this.id,
            brand = brand ?: this.brand,
            model = model ?: this.model,
            year = year ?: this.year,
            color = color ?: this.color,
            price = price ?: this.price,
            available = available ?: this.available,
            saleId = this.saleId
        )
    }

    fun sell(saleId: UUID?): Car {
        if (!available) {
            throw IllegalStateException("Car is already sold")
        }

        return this.copy(available = false, saleId = saleId)
    }
}