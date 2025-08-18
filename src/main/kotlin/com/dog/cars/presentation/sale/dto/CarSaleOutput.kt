package com.dog.cars.presentation.sale.dto

import com.dog.cars.domain.model.Sale
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

data class CarSaleOutput (
    val carId: UUID,
    val discountAmount: BigDecimal,
    val price: BigDecimal,
    val buyerDocument: String,
    val saleDate: LocalDateTime
)

fun Sale.toSaleOutput(): CarSaleOutput {
    return CarSaleOutput(
        carId = this.carId,
        discountAmount = this.discount,
        price = this.salePrice,
        buyerDocument = this.customerDocument,
        saleDate = this.saleDate
    )
}
