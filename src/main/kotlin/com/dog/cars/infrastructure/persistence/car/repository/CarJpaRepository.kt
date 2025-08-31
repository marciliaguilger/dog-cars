package com.dog.cars.infrastructure.persistence.car.repository

import com.dog.cars.infrastructure.persistence.car.model.CarModel
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface CarJpaRepository: JpaRepository<CarModel, UUID> {
    fun findByAvailable(available: Boolean): Collection<CarModel>
}