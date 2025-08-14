package com.dog.cars.infrastructure.persistence.mapper

import com.dog.cars.domain.model.Car
import com.dog.cars.infrastructure.persistence.model.CarModel

fun Car.toCarModel(): CarModel {
    return CarModel(
        this.id,
        this.brand,
        this.model,
        this.year,
        this.color,
        this.price,
        this.available,
        this.saleId
    )
}

fun CarModel.toCar(): Car {
    return Car(
        this.id,
        this.brand,
        this.model,
        this.year,
        this.color,
        this.price,
        this.available
    )
}