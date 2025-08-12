package com.dog.cars.domain.repository

import com.dog.cars.domain.model.Car
import java.util.*

interface CarRepository {
    fun upsert(car: Car)
    fun getById(id: UUID): Car?
    fun getAll(): Collection<Car>
}