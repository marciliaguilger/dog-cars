package com.dog.cars.application.port

import com.dog.cars.domain.payments.Payment
import com.dog.cars.domain.payments.PixQrCodePayment

interface PaymentGateway {
    fun pixPaymentGenerateQrCode(payment: Payment): PixQrCodePayment
}
