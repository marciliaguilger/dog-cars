package com.dog.cars.domain.payments.model

import java.math.BigDecimal
import java.util.*

data class PaymentItem (
    val id: UUID,
    val title: String,
    val unitPrice: BigDecimal,
    val quantity: Int,
)