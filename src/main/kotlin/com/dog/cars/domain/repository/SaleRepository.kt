package com.dog.cars.domain.repository

import com.dog.cars.domain.model.Sale

interface SaleRepository {
    fun save(sale: Sale)
    fun findByCarId(carId: String): Sale?
    fun findByCustomerDocument(customerDocument: String): Collection<Sale>
}