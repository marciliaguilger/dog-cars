package com.dog.cars.presentation.sale.dto

import com.dog.cars.domain.payments.model.PaymentStatus
import com.dog.cars.domain.payments.model.PaymentType
import com.dog.cars.domain.sales.enum.SaleStatus
import com.dog.cars.domain.sales.model.Sale
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

data class CarSaleOutput (
    val saleId: UUID,
    val status: SaleStatus,
    val carId: UUID,
    val discountAmount: BigDecimal,
    val price: BigDecimal,
    val buyerDocument: String,
    val saleDate: LocalDateTime,
    val payment: Payment?,
){
    data class Payment(
        val id: UUID,
        val totalAmount: BigDecimal,
        val status: PaymentStatus,
        val description: String,
        val type: PaymentType,
        val qrCode: String?,
        val customerDocument: String
    )
}

fun Sale.toSaleOutput(): CarSaleOutput {
    return CarSaleOutput(
        saleId = this.id,
        status = this.status,
        carId = this.carId,
        discountAmount = this.discount,
        price = this.salePrice,
        buyerDocument = this.customerDocument,
        saleDate = this.saleDate,
        payment = this.payment?.let {
            CarSaleOutput.Payment(
                id = it.id,
                totalAmount = it.totalAmount,
                status = it.status,
                description = it.description,
                type = it.type,
                qrCode = it.qrCode,
                customerDocument = it.customerDocument
            )
        }
    )
}
