package com.dog.cars.domain.model

import java.math.BigDecimal
import java.util.*

class Car (
        val id: UUID,
        val brand: String,
        val model: String,
        val year: Int,
        val color: String,
        val price: BigDecimal
) {
    companion object {
        fun create(
            brand: String,
            model: String,
            year: Int,
            color: String,
            price: BigDecimal
        ): Car {
            return Car(
                id = UUID.randomUUID(),
                brand = brand,
                model = model,
                year = year,
                color = color,
                price = price
            )
        }
    }


    fun update(
        brand: String? = null,
        model: String? = null,
        year: Int? = null,
        color: String? = null,
        price: BigDecimal? = null
    ): Car {
        return Car(
            id = this.id,
            brand = brand ?: this.brand,
            model = model ?: this.model,
            year = year ?: this.year,
            color = color ?: this.color,
            price = price ?: this.price
        )
    }
}