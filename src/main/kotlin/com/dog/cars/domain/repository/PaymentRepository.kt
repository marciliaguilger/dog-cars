package com.dog.cars.domain.repository

import com.dog.cars.domain.payments.model.Payment
import java.util.UUID

interface PaymentRepository {
    fun upsert(payment: Payment)
    fun getBySaleId(saleId: UUID): Payment?

    fun getById(paymentId: UUID): Payment?
}