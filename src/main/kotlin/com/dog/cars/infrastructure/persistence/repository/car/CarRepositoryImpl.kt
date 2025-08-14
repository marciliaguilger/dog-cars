package com.dog.cars.infrastructure.persistence.repository.car

import com.dog.cars.domain.model.Car
import com.dog.cars.domain.repository.CarRepository
import com.dog.cars.infrastructure.persistence.mapper.toCar
import com.dog.cars.infrastructure.persistence.mapper.toCarModel
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