package com.dog.cars.infrastructure.persistence.repository.sale

import com.dog.cars.domain.model.Sale
import com.dog.cars.domain.repository.SaleRepository
import com.dog.cars.infrastructure.persistence.mapper.toDomain
import com.dog.cars.infrastructure.persistence.mapper.toSaleModel
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class SaleRepositoryImpl(
    private val saleJpaRepository: SaleJpaRepository
): SaleRepository {
    override fun save(sale: Sale) {
        saleJpaRepository.save(sale.toSaleModel())
    }

    override fun findByCarId(carId: UUID): Sale? {
        return saleJpaRepository.findByCarId(carId)?.toDomain()
    }

    override fun findByCustomerDocument(customerDocument: String): Collection<Sale> {
        return saleJpaRepository.findAllByCustomerDocument(customerDocument)
            .map { it.toDomain() }
    }

    override fun findAll(): Collection<Sale> {
        return  saleJpaRepository.findAll()
            .map { it.toDomain() }
    }
}