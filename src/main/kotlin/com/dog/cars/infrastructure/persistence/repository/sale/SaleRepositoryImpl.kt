package com.dog.cars.infrastructure.persistence.repository.sale

import com.dog.cars.domain.sales.model.Sale
import com.dog.cars.application.port.SaleRepository
import com.dog.cars.infrastructure.persistence.mapper.toDomain
import com.dog.cars.infrastructure.persistence.mapper.toSaleModel
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class SaleRepositoryImpl(
    private val saleJpaRepository: SaleJpaRepository
): SaleRepository {
    override fun upsert(sale: Sale) {
        saleJpaRepository.save(sale.toSaleModel())
    }


    override fun findByCustomerDocument(customerDocument: String): Collection<Sale> {
        return saleJpaRepository.findAllByCustomerDocument(customerDocument)
            .map { it.toDomain() }
    }

    override fun findById(id: UUID): Sale {
        return saleJpaRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Sale with id $id not found") }
            .toDomain()
    }

    override fun findAll(): Collection<Sale> {
        return  saleJpaRepository.findAll()
            .map { it.toDomain() }
    }
}