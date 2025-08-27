package com.dog.cars.domain.payments.model

import java.math.BigDecimal
import java.util.*

data class Payment(
    val totalAmount:  BigDecimal,
    val status: PaymentStatus,
    val description: String,
    val saleId: UUID,
    val type: PaymentType,
    val qrCode: String?,
    val items: List<PaymentItem>,
)