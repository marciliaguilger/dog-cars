package com.dog.cars.application.port

import com.dog.cars.domain.car.model.Car
import java.util.*

interface CarRepository {
    fun upsert(car: Car)
    fun getById(id: UUID): Car?
    fun getAll(available: Boolean): Collection<Car>
}
