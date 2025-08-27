package com.dog.cars.presentation.sale.dto

import com.dog.cars.domain.payments.model.Payment
import com.dog.cars.domain.payments.model.PaymentStatus
import com.dog.cars.domain.payments.model.PaymentType
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