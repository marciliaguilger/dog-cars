package com.dog.cars.domain.payments.service

import com.dog.cars.domain.payments.gateway.PaymentGateway
import com.dog.cars.domain.payments.model.Payment
import com.dog.cars.domain.payments.model.PixQrCodePayment

class PixPaymentService(
    private val paymentGateway: PaymentGateway
) {
    fun generateQrCode(payment: Payment): PixQrCodePayment {
        return paymentGateway.pixPaymentGenerateQrCode(payment)
    }
}