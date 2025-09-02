package com.dog.cars.presentation.sale.dto

import com.dog.cars.domain.payments.Payment
import com.dog.cars.domain.payments.PaymentStatus
import com.dog.cars.domain.payments.PaymentType
import java.util.*

data class CarSalePaymentOutput(
    val saleId: UUID,
    val status: PaymentStatus,
    val paymentType: PaymentType,
    val qrCode: String?
)

fun Payment.toCarSalePaymentOutput() = CarSalePaymentOutput(
    saleId = this.saleId,
    status = this.status,
    paymentType = this.type,
    qrCode = this.qrCode
)