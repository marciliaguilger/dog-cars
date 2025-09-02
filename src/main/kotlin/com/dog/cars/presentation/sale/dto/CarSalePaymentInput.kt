package com.dog.cars.presentation.sale.dto

import com.dog.cars.domain.payments.PaymentType

data class CarSalePaymentInput (
    val paymentType: PaymentType
)