package com.dog.cars.infrastructure.config

import com.dog.cars.domain.repository.CarRepository
import com.dog.cars.domain.repository.PersonRepository
import com.dog.cars.domain.repository.SaleRepository
import com.dog.cars.domain.sales.usecase.SaleCarUseCase
import com.dog.cars.domain.car.usecase.ManageCarUseCase
import com.dog.cars.domain.payments.service.PixPaymentService
import com.dog.cars.domain.payments.usecase.PaymentUseCase
import com.dog.cars.domain.person.usecase.ManageCustomerUseCase
import com.dog.cars.domain.repository.PaymentRepository
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
        saleRepository: SaleRepository,
        paymentRepository: PaymentRepository
    ): SaleCarUseCase {
        return SaleCarUseCase(carRepository, personRepository, saleRepository, paymentRepository)
    }

    @Bean
    fun manageCustomerUseCase(
        personRepository: PersonRepository
    ): ManageCustomerUseCase {
        return ManageCustomerUseCase(personRepository)
    }

    @Bean
    fun paymentUseCase(
        personRepository: PersonRepository,
        saleRepository: SaleRepository,
        pixPaymentService: PixPaymentService,
        paymentRepository: PaymentRepository,
        carRepository: CarRepository
    ): PaymentUseCase {
        return PaymentUseCase(personRepository, saleRepository, paymentRepository, pixPaymentService, carRepository)
    }
}