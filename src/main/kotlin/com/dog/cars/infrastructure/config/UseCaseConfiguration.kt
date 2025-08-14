package com.dog.cars.infrastructure.config

import com.dog.cars.domain.repository.CarRepository
import com.dog.cars.domain.repository.PersonRepository
import com.dog.cars.domain.repository.SaleRepository
import com.dog.cars.domain.usecase.SellCarUseCase
import com.dog.cars.domain.usecase.ManageCarUseCase
import com.dog.cars.domain.usecase.ManageCustomerUseCase
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
    ): SellCarUseCase {
        return SellCarUseCase(carRepository, personRepository, saleRepository)
    }

    @Bean
    fun manageCustomerUseCase(
        personRepository: PersonRepository
    ): ManageCustomerUseCase {
        return ManageCustomerUseCase(personRepository)
    }
}