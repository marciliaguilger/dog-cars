package com.dog.cars.infrastructure.persistence.payment.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.util.UUID

@Entity
@Table(name = "payments")
class PaymentModel (
    @Id
    val id: UUID,
    val totalAmount: BigDecimal,
    val status: String,
    val description: String,
    val saleId: UUID,
    val type: String,
    val qrCode: String?,
    val customerDocument: String
    )