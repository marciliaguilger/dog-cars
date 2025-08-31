package com.dog.cars.presentation.car.dto

import com.dog.cars.domain.car.Car
import java.math.BigDecimal
import java.util.*

data class CarOutput (
    val id: UUID,
    val brand: String,
    val model: String,
    val year: Int,
    val color: String,
    val price: BigDecimal,
    val available: Boolean,
    val saleId: UUID?
)

fun Car.toOutput(): CarOutput {
    return CarOutput(
        id = id,
        brand = brand,
        model = model,
        year = year,
        color = color,
        price = price,
        available = available,
        saleId = saleId
    )
}