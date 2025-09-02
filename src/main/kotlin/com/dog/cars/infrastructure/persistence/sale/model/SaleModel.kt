package com.dog.cars.infrastructure.persistence.sale.model

import com.dog.cars.domain.sales.enum.SaleStatus
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name ="sales")
class SaleModel(
    @Id
    val id: UUID,
    val carId: UUID,
    val customerDocument: String,
    val saleDate: LocalDateTime,
    val salePrice: BigDecimal,
    val discount: BigDecimal,
    val status: SaleStatus,
    val cancellationReason: String? = null
)