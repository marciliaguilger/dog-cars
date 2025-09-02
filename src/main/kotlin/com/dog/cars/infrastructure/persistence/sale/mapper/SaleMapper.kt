package com.dog.cars.infrastructure.persistence.sale.mapper

import com.dog.cars.domain.sales.Sale
import com.dog.cars.infrastructure.persistence.sale.model.SaleModel

fun SaleModel.toDomain(): Sale {
    return Sale(
        id = id,
        carId = carId,
        customerDocument = customerDocument,
        saleDate = saleDate,
        salePrice = salePrice,
        discount = discount,
        status = status
    )
}

fun Sale.toSaleModel(): SaleModel {
    return SaleModel(
        id = id,
        carId = carId,
        customerDocument = customerDocument,
        saleDate = saleDate,
        salePrice = salePrice,
        discount = discount,
        status = status,
        cancellationReason = cancellationReason
    )
}