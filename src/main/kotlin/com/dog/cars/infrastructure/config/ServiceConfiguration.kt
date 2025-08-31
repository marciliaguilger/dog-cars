package com.dog.cars.infrastructure.config

import com.dog.cars.application.port.PaymentGateway
import com.dog.cars.application.service.PaymentService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ServiceConfiguration {

    @Bean
    fun paymentService(
        paymentGateway: PaymentGateway
    ): PaymentService {
        return PaymentService(paymentGateway)
    }
}