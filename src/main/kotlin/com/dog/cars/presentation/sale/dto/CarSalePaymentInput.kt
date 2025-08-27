package com.dog.cars.presentation.sale.dto

import com.dog.cars.domain.payments.model.PaymentType

data class CarSalePaymentInput (
    val paymentType: PaymentType
)