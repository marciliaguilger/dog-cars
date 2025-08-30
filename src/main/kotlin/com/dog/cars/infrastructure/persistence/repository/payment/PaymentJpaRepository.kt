package com.dog.cars.infrastructure.persistence.repository.payment
import com.dog.cars.infrastructure.persistence.model.PaymentModel
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface PaymentJpaRepository: JpaRepository<PaymentModel, UUID> {
    fun findBySaleId(saleId: UUID): PaymentModel?
}