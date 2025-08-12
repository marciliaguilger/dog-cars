package com.dog.cars.presentation.dto

import com.dog.cars.domain.model.Car
import java.math.BigDecimal

data class CarInput (
    val brand: String,
    val model: String,
    val year: Int,
    val color: String,
    val price: BigDecimal,
    val available: Boolean
)

fun CarInput.toDomain(): Car {
    return Car.create(
        brand = this.brand,
        model = this.model,
        year = this.year,
        color = this.color,
        price = this.price,
        available = this.available
    )
}