package com.dog.cars.infrastructure.persistence.payment.repository
import com.dog.cars.infrastructure.persistence.payment.model.PaymentModel
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface PaymentJpaRepository: JpaRepository<PaymentModel, UUID> {
    fun findBySaleId(saleId: UUID): PaymentModel?
}