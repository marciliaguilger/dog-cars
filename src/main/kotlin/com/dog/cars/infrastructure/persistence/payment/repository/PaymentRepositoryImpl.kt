package com.dog.cars.infrastructure.persistence.payment.repository

import com.dog.cars.domain.payments.Payment
import com.dog.cars.application.port.PaymentRepository
import com.dog.cars.infrastructure.persistence.payment.mapper.toDomain
import com.dog.cars.infrastructure.persistence.payment.mapper.toPaymentModel
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class PaymentRepositoryImpl(
    private val paymentJpaRepository: PaymentJpaRepository
): PaymentRepository {
    override fun upsert(payment: Payment) {
        val paymentModel = payment.toPaymentModel()
        paymentJpaRepository.save(paymentModel)
    }

    override fun getBySaleId(saleId: UUID): Payment? {
       return paymentJpaRepository.findBySaleId(saleId)?.toDomain()
    }

    override fun getById(id: UUID): Payment? {
        return paymentJpaRepository.findById(id)
            .map { it.toDomain() }
            .orElse(null)
    }
}