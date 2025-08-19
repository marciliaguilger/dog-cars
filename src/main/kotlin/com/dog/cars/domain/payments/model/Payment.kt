package com.dog.cars.domain.payments.model

import java.math.BigDecimal
import java.util.*

data class Payment(
    val totalAmount:  BigDecimal,
    val description: String,
    val saleId: UUID,
    val items: List<PaymentItem>,
)