package com.dog.cars.domain.payments.usecase

import com.dog.cars.domain.car.enum.SaleStatus
import com.dog.cars.domain.payments.model.Payment
import com.dog.cars.domain.payments.model.PaymentItem
import com.dog.cars.domain.payments.service.PixPaymentService
import com.dog.cars.domain.repository.CarRepository
import com.dog.cars.domain.repository.PersonRepository
import com.dog.cars.domain.repository.SaleRepository
import java.util.*

class PaymentUseCase(
    private val personRepository: PersonRepository,
    private val saleRepository: SaleRepository,
    private val pixPaymentService: PixPaymentService

) {

    fun pay(saleId: UUID) {
        val sale = saleRepository.findById(saleId)

        if (sale.status != SaleStatus.CREATED) {
            throw IllegalStateException("Sale with id $saleId is not in a valid state for payment")
        }

        val customer = personRepository.getByDocument(sale.customerDocument)
            ?: throw IllegalArgumentException("Customer with document ${sale.customerDocument} not found")

        val payment = Payment(
            totalAmount = sale.salePrice,
            description = "Payment for sale ${sale.id} by ${customer.name}",
            saleId = sale.id,
            items = listOf(PaymentItem(
                id = sale.carId,
                title = "Sale of car ${sale.carId}",
                unitPrice = sale.salePrice,
                quantity = 1
            ))
        )

        val pixPaymentResponse = pixPaymentService.generateQrCode(payment)

        val updatedSale = sale.copy(status = SaleStatus.APPROVED)

        saleRepository.save(updatedSale)
    }
}