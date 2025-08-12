package com.dog.cars.infrastructure.config

import com.dog.cars.domain.repository.CarRepository
import com.dog.cars.domain.repository.PersonRepository
import com.dog.cars.domain.repository.SaleRepository
import com.dog.cars.domain.usecase.SellCarUseCase
import com.dog.cars.domain.usecase.UpdateCarUseCase
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UseCaseConfiguration {
    @Bean
    fun updateCarUseCase(carRepository: CarRepository): UpdateCarUseCase {
        return UpdateCarUseCase(carRepository)
    }

    @Bean
    fun sellCarUseCase(
        carRepository: CarRepository,
        personRepository: PersonRepository,
        saleRepository: SaleRepository
    ): SellCarUseCase {
        return SellCarUseCase(carRepository, personRepository, saleRepository)
    }
}