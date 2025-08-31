package com.dog.cars.application.usecase

import com.dog.cars.domain.sales.enum.SaleStatus
import com.dog.cars.domain.payments.Payment
import com.dog.cars.domain.payments.PaymentItem
import com.dog.cars.domain.payments.PaymentStatus
import com.dog.cars.domain.payments.PaymentType
import com.dog.cars.application.service.PaymentService
import com.dog.cars.application.port.CarRepository
import com.dog.cars.application.port.PaymentRepository
import com.dog.cars.application.port.PersonRepository
import com.dog.cars.application.port.SaleRepository
import java.util.*

class PaymentUseCase(
    private val personRepository: PersonRepository,
    private val saleRepository: SaleRepository,
    private val paymentRepository: PaymentRepository,
    private val paymentService: PaymentService,
    private val carRepository: CarRepository

) {

    fun createPayment(saleId: UUID, paymentType: PaymentType): Payment {
        val sale = saleRepository.findById(saleId)

        if (sale.status != SaleStatus.CREATED) {
            throw IllegalStateException("Sale with id $saleId is not in a valid state for payment")
        }

        val customer = personRepository.getByDocument(sale.customerDocument)
            ?: throw IllegalArgumentException("Customer with document ${sale.customerDocument} not found")

        val payment = Payment(
            id = UUID.randomUUID(),
            totalAmount = sale.salePrice,
            status = PaymentStatus.CREATED,
            description = "Payment for sale ${sale.id} by ${customer.name}",
            saleId = sale.id,
            type = paymentType,
            qrCode = "",
            customerDocument = customer.document,
            items = listOf(PaymentItem(
                id = sale.carId,
                title = "Sale of car ${sale.carId}",
                unitPrice = sale.salePrice,
                quantity = 1
            ))
        )

        val pixPaymentResponse = paymentService.generateQrCode(payment)

        val updatedPayment = payment.copy(qrCode = pixPaymentResponse.pixKey)

        val updatedSale = sale.copy(status = SaleStatus.WAITING_PAYMENT, payment = updatedPayment)

        saleRepository.upsert(updatedSale)
        paymentRepository.upsert(updatedPayment)

        return updatedPayment
    }

    fun confirmPayment(paymentId: UUID) {
        val payment = paymentRepository.getById(paymentId)
            ?: throw IllegalArgumentException("Payment with id $paymentId not found")

        val sale = saleRepository.findById(payment.saleId)

        val updatedPayment = payment.copy(status = PaymentStatus.APPROVED)
        val updatedSale = sale.copy(status = SaleStatus.APPROVED, payment = updatedPayment)

        saleRepository.upsert(updatedSale)
        paymentRepository.upsert(updatedPayment)
    }

    fun rejectPayment(paymentId: UUID) {
        val payment = paymentRepository.getById(paymentId)
            ?: throw IllegalArgumentException("Payment with id $paymentId not found")

        val updatedPayment = payment.copy(status = PaymentStatus.REJECTED)
        paymentRepository.upsert(updatedPayment)
    }

    fun cancelPayment(paymentId: UUID) {
        val payment = paymentRepository.getById(paymentId)
            ?: throw IllegalArgumentException("Payment with id $paymentId not found")

        val sale = saleRepository.findById(payment.saleId)

        val car = carRepository.getById(sale.carId)
            ?: throw IllegalArgumentException("Car with id ${sale.carId} not found")

        if (sale.status != SaleStatus.WAITING_PAYMENT) {
            throw IllegalStateException("Sale with id ${sale.id} is not in a valid state for payment")
        }

        val updatedPayment = payment.copy(status = PaymentStatus.CANCELLED)
        val updatedSale = sale.copy(status = SaleStatus.CANCELLED, payment = updatedPayment)
        car.undoSale()

        paymentRepository.upsert(updatedPayment)
        saleRepository.upsert(updatedSale)
        carRepository.upsert(car)
    }
}
