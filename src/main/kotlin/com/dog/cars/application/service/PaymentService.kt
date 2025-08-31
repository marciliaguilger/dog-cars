package com.dog.cars.application.service

import com.dog.cars.application.port.PaymentGateway
import com.dog.cars.domain.payments.model.Payment
import com.dog.cars.domain.payments.model.PixQrCodePayment

class PaymentService(
    private val paymentGateway: PaymentGateway
) {
    fun generateQrCode(payment: Payment): PixQrCodePayment {
        return paymentGateway.pixPaymentGenerateQrCode(payment)
    }
}
