package com.dog.cars.presentation.sale.dto

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

data class CarSellInput (
    val carId: UUID,
    val discountAmount: BigDecimal,
    val buyerDocument: String,
    val saleDate: LocalDateTime
)
