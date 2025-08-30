package com.dog.cars.infrastructure.persistence.mapper

import com.dog.cars.domain.payments.model.Payment
import com.dog.cars.domain.payments.model.PaymentStatus
import com.dog.cars.domain.payments.model.PaymentType
import com.dog.cars.infrastructure.persistence.model.PaymentModel

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