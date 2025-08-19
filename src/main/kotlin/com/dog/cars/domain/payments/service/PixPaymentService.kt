package com.dog.cars.domain.payments.service

import com.dog.cars.domain.payments.gateway.PixPaymentGateway
import com.dog.cars.domain.payments.model.Payment
import com.dog.cars.domain.payments.model.PixQrCodePayment

class PixPaymentService(
    private val pixPaymentGateway: PixPaymentGateway
) {
    fun generateQrCode(payment: Payment): PixQrCodePayment {
        return pixPaymentGateway.generateQrCode(payment)
    }
}