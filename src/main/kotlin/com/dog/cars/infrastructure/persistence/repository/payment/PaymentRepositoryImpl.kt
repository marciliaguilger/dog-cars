package com.dog.cars.infrastructure.persistence.repository.payment

import com.dog.cars.domain.payments.model.Payment
import com.dog.cars.application.port.PaymentRepository
import com.dog.cars.infrastructure.persistence.mapper.toDomain
import com.dog.cars.infrastructure.persistence.mapper.toPaymentModel
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

    override fun getById(paymentId: UUID): Payment? {
        return paymentJpaRepository.findById(paymentId)
            .map { it.toDomain() }
            .orElse(null)
    }
}