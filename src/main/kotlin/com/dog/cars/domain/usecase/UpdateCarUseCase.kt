package com.dog.cars.domain.usecase

import com.dog.cars.domain.model.Car
import com.dog.cars.domain.repository.CarRepository

class UpdateCarUseCase (
    private val carRepository: CarRepository
) {

    fun execute(car: Car): Car {
        carRepository.upsert(car)
        return car
    }
}