package com.dog.cars.infrastructure.config

import com.dog.cars.domain.payments.gateway.PaymentGateway
import com.dog.cars.domain.payments.service.PixPaymentService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ServiceConfiguration {

    @Bean
    fun pixPaymentService(
        paymentGateway: PaymentGateway
    ): PixPaymentService {
        return PixPaymentService(paymentGateway)
    }
}