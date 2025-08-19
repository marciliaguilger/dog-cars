package com.dog.cars.infrastructure.config

import com.dog.cars.domain.repository.CarRepository
import com.dog.cars.domain.repository.PersonRepository
import com.dog.cars.domain.repository.SaleRepository
import com.dog.cars.domain.car.usecase.SaleCarUseCase
import com.dog.cars.domain.car.usecase.ManageCarUseCase
import com.dog.cars.domain.person.usecase.ManageCustomerUseCase
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UseCaseConfiguration {
    @Bean
    fun manageCarUseCase(carRepository: CarRepository): ManageCarUseCase {
        return ManageCarUseCase(carRepository)
    }

    @Bean
    fun sellCarUseCase(
        carRepository: CarRepository,
        personRepository: PersonRepository,
        saleRepository: SaleRepository
    ): SaleCarUseCase {
        return SaleCarUseCase(carRepository, personRepository, saleRepository)
    }

    @Bean
    fun manageCustomerUseCase(
        personRepository: PersonRepository
    ): ManageCustomerUseCase {
        return ManageCustomerUseCase(personRepository)
    }
}