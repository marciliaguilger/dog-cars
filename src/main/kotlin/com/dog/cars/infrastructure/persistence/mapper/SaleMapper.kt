package com.dog.cars.infrastructure.persistence.mapper

import com.dog.cars.domain.model.Sale
import com.dog.cars.infrastructure.persistence.model.SaleModel

fun SaleModel.toDomain(): Sale {
    return Sale(
        id = id,
        carId = carId,
        customerDocument = customerDocument,
        saleDate = saleDate,
        salePrice = salePrice
    )
}

fun Sale.toSaleModel(): SaleModel {
    return SaleModel(
        id = id,
        carId = carId,
        customerDocument = customerDocument,
        saleDate = saleDate,
        salePrice = salePrice
    )
}