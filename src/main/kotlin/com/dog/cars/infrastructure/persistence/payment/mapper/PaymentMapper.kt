package com.dog.cars.infrastructure.persistence.payment.mapper

import com.dog.cars.domain.payments.Payment
import com.dog.cars.domain.payments.PaymentStatus
import com.dog.cars.domain.payments.PaymentType
import com.dog.cars.infrastructure.persistence.payment.model.PaymentModel

fun Payment.toPaymentModel(): PaymentModel {
    return PaymentModel(
        id = this.id,
        totalAmount = this.totalAmount,
        status = this.status.name,
        description = this.description,
        saleId = this.saleId,
        type = this.type.name,
        qrCode = this.qrCode,
        customerDocument = this.customerDocument
    )
}

fun PaymentModel.toDomain(): Payment {
    return Payment(
        id = this.id,
        totalAmount = this.totalAmount,
        status = PaymentStatus.valueOf(this.status),
        description = this.description,
        saleId = this.saleId,
        type = PaymentType.valueOf(this.type),
        qrCode = this.qrCode,
        customerDocument = this.customerDocument,
        items = emptyList()
    )
}