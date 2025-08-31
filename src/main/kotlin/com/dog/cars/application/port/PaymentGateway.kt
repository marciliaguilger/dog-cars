package com.dog.cars.application.port

import com.dog.cars.domain.payments.model.Payment
import com.dog.cars.domain.payments.model.PixQrCodePayment

interface PaymentGateway {
    fun pixPaymentGenerateQrCode(payment: Payment): PixQrCodePayment
}
