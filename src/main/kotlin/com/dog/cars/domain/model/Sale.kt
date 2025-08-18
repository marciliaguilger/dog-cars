package com.dog.cars.domain.model

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

data class Sale (
    val id: UUID,
    val carId: UUID,
    val customerDocument: String,
    val saleDate: LocalDateTime,
    val salePrice: BigDecimal,
    val discount: BigDecimal
)