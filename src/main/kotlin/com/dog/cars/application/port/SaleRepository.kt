package com.dog.cars.application.port

import com.dog.cars.domain.sales.Sale
import java.util.*

interface SaleRepository {
    fun upsert(sale: Sale)
    fun findById(id: UUID): Sale
    fun findAll(): Collection<Sale>
    fun findByCustomerDocument(customerDocument: String): Collection<Sale>
}
