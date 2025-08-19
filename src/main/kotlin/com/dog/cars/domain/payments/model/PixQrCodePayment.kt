package com.dog.cars.domain.payments.model

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

data class PixQrCodePayment(
    val pixKey: String,
    val createdDate: LocalDateTime,
    val status: String,
    val type: String = "qr",
    val totalAmount: BigDecimal,
    val externalSaleId: UUID,
)
