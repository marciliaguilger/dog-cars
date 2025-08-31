package com.dog.cars.application.usecase

import com.dog.cars.domain.car.model.Car
import com.dog.cars.application.port.CarRepository

class ManageCarUseCase (
    private val carRepository: CarRepository
) {

    fun execute(car: Car): Car {
        carRepository.upsert(car)
        return car
    }

    fun getCars(available: Boolean = true): Collection<Car> {
        return carRepository.getAll(available).sortedBy { it.price }
    }
}
