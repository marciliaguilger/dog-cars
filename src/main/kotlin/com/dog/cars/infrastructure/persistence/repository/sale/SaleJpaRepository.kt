package com.dog.cars.infrastructure.persistence.repository.sale

import com.dog.cars.infrastructure.persistence.model.SaleModel
import org.springframework.data.jpa.repository.JpaRepository

interface SaleJpaRepository: JpaRepository<SaleModel, String> {
    fun save(sale: SaleModel)
    fun findByCarId(carId: String): SaleModel?
    fun findAllByCustomerDocument(customerDocument: String): Collection<SaleModel>
}