package com.dog.cars.infrastructure.persistence.car.repository

import com.dog.cars.domain.car.Car
import com.dog.cars.application.port.CarRepository
import com.dog.cars.infrastructure.persistence.car.mapper.toCar
import com.dog.cars.infrastructure.persistence.car.mapper.toCarModel
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class CarRepositoryImpl(
    private val carJpaRepository: CarJpaRepository
): CarRepository {
    override fun upsert(car: Car) {
       val model = car.toCarModel()
        carJpaRepository.save(model)
    }

    override fun getById(id: UUID): Car? {
        return carJpaRepository.findByIdOrNull(id)?.toCar()
    }

    override fun getAll(available: Boolean): Collection<Car> {
        return carJpaRepository.findByAvailable(available).map { it.toCar() }
    }
}