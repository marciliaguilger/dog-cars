package com.dog.cars.application.config

import com.dog.cars.application.port.CarRepository
import com.dog.cars.application.port.PersonRepository
import com.dog.cars.application.port.SaleRepository
import com.dog.cars.application.usecase.SaleCarUseCase
import com.dog.cars.application.usecase.ManageCarUseCase
import com.dog.cars.application.service.PaymentService
import com.dog.cars.application.usecase.PaymentUseCase
import com.dog.cars.application.usecase.ManageCustomerUseCase
import com.dog.cars.application.port.PaymentRepository
import com.dog.cars.application.port.PaymentGateway
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
    fun paymentService(paymentGateway: PaymentGateway): PaymentService {
        return PaymentService(paymentGateway)
    }

    @Bean
    fun paymentUseCase(
        personRepository: PersonRepository,
        saleRepository: SaleRepository,
        paymentService: PaymentService,
        paymentRepository: PaymentRepository,
        carRepository: CarRepository
    ): PaymentUseCase {
        return PaymentUseCase(personRepository, saleRepository, paymentRepository, paymentService, carRepository)
    }
}
