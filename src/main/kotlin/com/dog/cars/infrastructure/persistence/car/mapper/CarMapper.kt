package com.dog.cars.infrastructure.persistence.car.mapper

import com.dog.cars.domain.car.Car
import com.dog.cars.infrastructure.persistence.car.model.CarModel

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
        this.available,
        this.saleId
    )
}