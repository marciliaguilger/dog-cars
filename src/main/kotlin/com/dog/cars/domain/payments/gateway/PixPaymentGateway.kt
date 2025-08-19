package com.dog.cars.domain.payments.gateway

import com.dog.cars.domain.payments.model.Payment
import com.dog.cars.domain.payments.model.PixQrCodePayment

interface PixPaymentGateway {
    fun generateQrCode(payment: Payment): PixQrCodePayment
}