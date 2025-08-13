package com.dog.cars.infrastructure.persistence.repository.sale

import com.dog.cars.infrastructure.persistence.model.SaleModel
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface SaleJpaRepository: JpaRepository<SaleModel, UUID> {
    fun save(sale: SaleModel)
    fun findByCarId(carId: UUID): SaleModel?
    fun findAllByCustomerDocument(customerDocument: String): Collection<SaleModel>
}