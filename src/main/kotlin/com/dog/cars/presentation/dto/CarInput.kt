package com.dog.cars.presentation.dto

import java.math.BigDecimal

data class CarInput (
    val brand: String,
    val model: String,
    val year: Int,
    val color: String,
    val price: BigDecimal
)