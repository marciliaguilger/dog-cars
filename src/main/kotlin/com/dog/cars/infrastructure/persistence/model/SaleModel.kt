package com.dog.cars.infrastructure.persistence.model

import com.dog.cars.domain.car.enum.SaleStatus
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

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
    val status: SaleStatus
)