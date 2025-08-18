package com.dog.cars.domain.repository

import com.dog.cars.domain.model.Sale
import java.util.UUID

interface SaleRepository {
    fun save(sale: Sale)
    fun findByCarId(carId: UUID): Sale?
    fun findByCustomerDocument(customerDocument: String): Collection<Sale>
    fun findAll(): Collection<Sale>
}