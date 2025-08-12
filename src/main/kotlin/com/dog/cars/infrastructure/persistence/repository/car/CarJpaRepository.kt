package com.dog.cars.infrastructure.persistence.repository.car

import com.dog.cars.infrastructure.persistence.model.CarModel
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface CarJpaRepository: JpaRepository<CarModel, UUID> {
}