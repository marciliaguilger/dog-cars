package com.dog.cars.application.port

import com.dog.cars.domain.payments.Payment
import java.util.*

interface PaymentRepository {
    fun upsert(payment: Payment)
    fun getById(id: UUID): Payment?
    fun getBySaleId(saleId: UUID): Payment?
}
