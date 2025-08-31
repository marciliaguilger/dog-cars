package com.dog.cars.domain.sales.usecase

import com.dog.cars.domain.sales.enum.SaleStatus
import com.dog.cars.domain.person.model.Buyer
import com.dog.cars.domain.car.model.Car
import com.dog.cars.domain.person.model.Person
import com.dog.cars.domain.sales.model.Sale
import com.dog.cars.domain.repository.CarRepository
import com.dog.cars.domain.repository.PaymentRepository
import com.dog.cars.domain.repository.PersonRepository
import com.dog.cars.domain.repository.SaleRepository
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

class SaleCarUseCase(
    private val carRepository: CarRepository,
    private val personRepository: PersonRepository,
    private val saleRepository: SaleRepository,
    private val paymentRepository: PaymentRepository,
) {
    fun sale(carId: UUID, buyerDocument: String, saleDate: LocalDateTime, discountAmount: BigDecimal): Sale {
        val car = carRepository.getById(carId)
            ?: throw IllegalArgumentException("Car with id $carId not found")

        if (!car.available) {
            throw IllegalStateException("Car with id $carId is already sold")
        }

        val person = personRepository.getByDocument(buyerDocument)
            ?: throw IllegalArgumentException("Buyer with document $buyerDocument not found")

        val buyer = Buyer(
            document = person.document,
            name = person.name,
        )
        val sale = createSale(car, buyer, saleDate, discountAmount)
        val soldCar = car.sell(sale.id, sale.salePrice)
        carRepository.upsert(soldCar)
        saleRepository.upsert(sale)
        return sale
    }

    fun getAll(): Collection<Sale> {
        val sales = saleRepository.findAll()

        return sales.map { sale ->
            val payment = paymentRepository.getBySaleId(sale.id)
            sale.copy(payment = payment)
        }
    }

    fun getByCustomerDocument(customerDocument: String): Collection<Sale> {
        if (customerDocument.isBlank()) {
            throw IllegalArgumentException("Buyer document cannot be blank")
        }

        val sale = saleRepository.findByCustomerDocument(customerDocument)

        val populatedSales = sale.map {
            val payment = paymentRepository.getBySaleId(it.id)
            it.copy(payment = payment)
        }

        return populatedSales
    }

    fun cancel(saleId: UUID, reason: String){
        val sale = saleRepository.findById(saleId)

        if (sale.status != SaleStatus.CREATED && sale.status != SaleStatus.WAITING_PAYMENT) {
            throw IllegalStateException("Sale with id $saleId is not in a valid state for payment")
        }

        val car = carRepository.getById(sale.carId)
            ?: throw IllegalArgumentException("Car with id ${sale.carId} not found")

        val canceledSale = sale.copy(status = SaleStatus.CANCELLED, cancellationReason = reason)
        val availableCar = car.copy(available = true, saleId = null)

        carRepository.upsert(availableCar)
        saleRepository.upsert(canceledSale)
    }

    private fun createSale(car: Car, buyer: Person, saleDate: LocalDateTime, discount: BigDecimal): Sale {

        validateDiscount(discount, car.price)

        val salePrice = car.price - discount

        return Sale(
            id = UUID.randomUUID(),
            carId = car.id,
            customerDocument = buyer.document,
            saleDate = saleDate,
            salePrice = salePrice,
            discount = discount,
            status = SaleStatus.CREATED
        )
    }

    private fun validateDiscount(discount: BigDecimal, carPrice: BigDecimal) {
        if (discount < BigDecimal.ZERO || discount > (carPrice * BigDecimal("0.2"))) {
            throw IllegalArgumentException("Invalid discount amount: $discount for car price: $carPrice")
        }
    }
}