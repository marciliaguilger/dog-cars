package com.dog.cars.infrastructure.gateway

import com.dog.cars.domain.payments.gateway.PixPaymentGateway
import com.dog.cars.domain.payments.model.Payment
import com.dog.cars.domain.payments.model.PixQrCodePayment

class PixPaymentGatewayImpl: PixPaymentGateway {
    override fun generateQrCode(payment: Payment): PixQrCodePayment {
        TODO("Not yet implemented")
    }
}