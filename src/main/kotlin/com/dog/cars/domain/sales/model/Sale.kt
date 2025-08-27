package com.dog.cars.domain.sales.model

import com.dog.cars.domain.payments.model.Payment
import com.dog.cars.domain.sales.enum.SaleStatus
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

data class Sale (
    val id: UUID,
    val carId: UUID,
    val customerDocument: String,
    val saleDate: LocalDateTime,
    val salePrice: BigDecimal,
    val discount: BigDecimal,
    val status: SaleStatus,
    val payment: Payment? = null
)